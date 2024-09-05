package com.example.objectdetection.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.objectdetection.R
import com.example.objectdetection.base.BaseFragment
import com.example.objectdetection.base.ViewEvent
import com.example.objectdetection.base.ViewState
import com.example.objectdetection.databinding.FragmentSplashBinding
import com.example.objectdetection.ext.routeLoginFragment
import com.example.objectdetection.ui.mypage.MyPageFragment.Companion.KEY_LOGOUT
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity().intent.getBooleanExtra(KEY_LOGOUT, false)) {
            routeLoginFragment()
        }
    }

    override fun initUi() {}

    override fun onChangedViewState(state: ViewState) {
        when (state) {
            is SplashViewState.RouteLogin -> routeLoginFragment()
        }
    }

    override fun onChangeViewEvent(event: ViewEvent) {}


}