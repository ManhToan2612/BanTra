package com.toan04.bantra.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.toan04.bantra.DAO.GioHangDAO;
import com.toan04.bantra.DAO.SanPhamDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.GioHangAdapter;
import com.toan04.bantra.adapter.SanPhamHAdapter;
import com.toan04.bantra.model.GioHang;
import com.toan04.bantra.model.SanPham;
import com.toan04.bantra.model.Slideiten;

import java.util.ArrayList;
import java.util.List;


public class HomeFreagment extends Fragment {

    RecyclerView rcv;
    SanPhamDAO spdao;
    SanPhamHAdapter sanPhamHomeAdapter;
    ArrayList<SanPham> list= new ArrayList<>();
    ArrayList<SanPham> tempListSanPham= new ArrayList<>();
    GioHangAdapter gioHangAdapter;
    GioHangDAO gioHangDAO;
    ArrayList<GioHang> listGioHang = new ArrayList<>();
    EditText edSearch;


    public HomeFreagment() {
        // Required empty public constructor
    }

    ViewPager2 viewpage;
    private List<Slideiten> slidelist;
    private Handler slideHanlder = new Handler(Looper.getMainLooper());



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_freagment, container, false);
        rcv= view.findViewById(R.id.rcvSanPham);
        spdao= new SanPhamDAO(getContext());
        list = spdao.getDSSanPham();
        tempListSanPham = spdao.getDSSanPham();
        sanPhamHomeAdapter = new SanPhamHAdapter(getContext(), list);
        rcv.setAdapter(sanPhamHomeAdapter);
        gioHangDAO = new GioHangDAO(getContext());
        gioHangAdapter = new GioHangAdapter(new ArrayList<>(),getContext());
        edSearch = view.findViewById(R.id.edSearch);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                ArrayList<SanPham> filteredList = new ArrayList<>();
                for (SanPham sanPham : list) {
                    if (sanPham.getTenSanPham().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(sanPham);
                    }
                }
                sanPhamHomeAdapter.setSanPhamList(filteredList);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        rcv.setLayoutManager(gridLayoutManager);
//        sanPhamHomeAdapter = new SanPhamHAdapter(getContext(),list);
//        rcv.setAdapter(sanPhamHomeAdapter);
//        sanPhamHomeAdapter.notifyDataSetChanged();
//
//        sanPhamHomeAdapter.setOnAddToCartClickListener(new sanPhamHomeAdapter.OnAddToCartClickListener() {
//            @Override
//            public void onAddToCartClick(SanPham sanPham) {
//                themVaoGio(sanPham);
//            }
//        });
//
//        sanPhamHomeAdapter.setOnItemClickListener(position -> {
//            Bundle bundle = new Bundle();
//            bundle.putInt("maGiay", list.get(position).getMaSanPham());
//            bundle.putString("tenLoai", list.get(position).getTenLoai());
//            SanPhamCTFragment sanPhamCTFragment = new SanPhamCTFragment();
//            sanPhamCTFragment.setArguments(bundle);
//
//            FragmentManager fragmentManager = getParentFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frglayout, sanPhamCTFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        });

        return view;
    }

    private int getSoLuongSp(int maSanPham) {
        for (SanPham sanPham : list) {
            if (sanPham.getMaSanPham() == maSanPham) {
                return sanPham.getSoLuong();
            }
        }
        return 0; // Trả về 0 nếu không tìm thấy sản phẩm
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return new ViewModelStore();
    }


    private Runnable sildeRunnable = new Runnable() {
        @Override
        public void run() {
//            binding.viewpage.setCurrentItem(binding.viewpage.getCurrentItem() + 1);
            int vitri =viewpage.getCurrentItem();
            if (vitri == slidelist.size() - 1) {
                viewpage.setCurrentItem(0);
            } else {
                viewpage.setCurrentItem(vitri + 1);
            }
        }
    };

    private void themVaoGio(SanPham sanPham) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String maad = sharedPreferences.getString("maAD", "");
        int maSanPham = sanPham.getMaSanPham();
        int slSanPham = getSoLuongSp(maSanPham);
        listGioHang = gioHangDAO.getDanhSachGioHangByMaNguoiDung(maad);

        boolean isProductInCart = false;

        for (GioHang gioHang : listGioHang) {
            if (gioHang.getMaGioHang() == maSanPham) {
                isProductInCart = true;
                if (gioHang.getSoLuongMua() < slSanPham) {
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    gioHangDAO.updateGioHang(gioHang);
                    Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

        if (!isProductInCart) {
            if (slSanPham > 0) {
                gioHangDAO.insertGioHang(new GioHang(maSanPham, maad, 1));
            } else {
                Toast.makeText(getActivity(), "Sản phẩm hết hàng", Toast.LENGTH_SHORT).show();
            }
        }
    }


}