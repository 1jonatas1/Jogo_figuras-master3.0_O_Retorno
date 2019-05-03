package br.com.heitor.jogo_figuras;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
    private FigurasView figurasView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View raiz =
                inflater.inflate (
                        R.layout.fragment_main,
                        container,
                        false
                );
        figurasView = raiz.findViewById(R.id.figurasView);
        return raiz;
    }
    public FigurasView getFigurasView() {
        return figurasView;
    }
}
