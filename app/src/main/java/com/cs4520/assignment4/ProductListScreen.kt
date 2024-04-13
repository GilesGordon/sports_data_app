package com.cs4520.assignment4

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cs4520.assignment4.databases.SportsRepository

@Composable
fun ProductListScreen(sport: String, repository: SportsRepository) {
    val viewModel = ProductListViewModel(repository)

    val products by remember { viewModel.products }
    val isLoading by remember { viewModel.isLoading }
    val isError by remember { viewModel.isError }

    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    val isNetworkAvailable =
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

    LaunchedEffect(true) {
//        viewModel.scheduleProductRefresh(context)
        viewModel.loadProducts(isNetworkAvailable, sport) //specify sport
    }
}
//    ConstraintLayout(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        val (textView, recyclerView, progressBar) = createRefs()
//        if (products.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .constrainAs(recyclerView) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            ) {
//                items(products) { product ->
//                    ProductItem(product)
//                }
//            }
//        }
//        if (!isLoading && (isError || products.isEmpty())) {
//            Text(
//                text = stringResource(R.string.no_products_available),
//                style = TextStyle(
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 30.sp
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .constrainAs(textView) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//        }
//
//        if (isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .constrainAs(progressBar) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//        }
//    }
//}

//@Composable
//fun ProductItem(product: Product) {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(
//                when (product.type) {
//                    "Equipment" -> Color(0xFFE06666)
//                    "Food" -> Color(0xFFFFD965)
//                    else -> Color(0xFFFFD965)
//                }
//            )
//    ) {
//        val (image, name, date, price) = createRefs()
//
//        Image(
//            painter = painterResource(
//                when (product.type) {
//                    "Equipment" -> R.drawable.equipment
//                    "Food" -> R.drawable.food
//                    else -> 0
//                }
//            ),
//            contentDescription = null,
//            modifier = Modifier
//                .size(48.dp)
//                .constrainAs(image) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(parent.bottom)
//                }
//                .padding(start = 10.dp)
//        )
//
//        Text(
//            text = product.name,
//            color = Color.Black,
//            modifier = Modifier
//                .constrainAs(name) {
//                    top.linkTo(parent.top, margin = 15.dp)
//                    start.linkTo(image.end, margin = 10.dp)
//                }
//        )
//
//        if (product.expiryDate != null) {
//            Text(
//                text = product.expiryDate,
//                color = Color.Black,
//                modifier = Modifier
//                    .constrainAs(date) {
//                        top.linkTo(name.bottom, margin = 10.dp)
//                        start.linkTo(image.end, margin = 10.dp)
//                    }
//            )
//        }
//
//        Text(
//            text = product.price.toString(),
//            color = Color.Black,
//            modifier = Modifier
//                .constrainAs(price) {
//                    when (product.type) {
//                        "Equipment" -> top.linkTo(name.bottom, margin = 10.dp)
//                        "Food" -> top.linkTo(date.bottom, margin = 10.dp)
//                    }
//                    start.linkTo(image.end, margin = 10.dp)
//                    bottom.linkTo(parent.bottom, margin = 15.dp)
//                }
//        )
//    }
//}