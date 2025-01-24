package com.djordjekrutil.tmdb.feature.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.djordjekrutil.tmdb.ui.theme.TmdbTheme
import androidx.navigation.compose.rememberNavController
import com.djordjekrutil.tmdb.core.platform.BaseActivity
import com.djordjekrutil.tmdb.feature.navigation.Navigation
import com.djordjekrutil.tmdb.ui.theme.primary
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TmdbTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primary
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}