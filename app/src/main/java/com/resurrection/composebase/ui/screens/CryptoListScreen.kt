package com.atilsamancioglu.cryptocrazycompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.resurrection.composebase.data.model.CryptoList
import com.resurrection.composebase.data.model.CryptoListItem
import com.resurrection.composebase.ui.screens.viewmodel.CryptoListViewModel
import com.resurrection.composebase.util.resource.*

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                "Crypto Crazy", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                //viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            EvenChanger()
            CryptoList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun CryptoList(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    println("-----> CryptoList yenilendi ")

    viewModel.cryptoList.observeState(
        success = {
            println("---> success çalıştı")
            it?.let {
                CryptoListView(cryptos = it, navController = navController)
            }
        }, loading = {
            println("---> loading çalıştı")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)

            }
        },
        error = {
            println("----> error çalıştı")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                RetryView(error = it!!.message) {
                    viewModel.loadCryptos()
                }
            }
        })

}

@Composable
fun <T> MutableState<StateResource<T>>.observeState(
    success: @Composable (T?) -> Unit,
    loading: @Composable (() -> Unit)? = null,
    error: @Composable ((Throwable?) -> Unit)? = null
) {
    val data by remember { this.value.data }
    val errorMessage by remember { this.value.message }
    val isLoading by remember { this.value.loading }
    val status by remember { this.value.status }
    when (status) {
        Status.SUCCESS -> success.invoke(data)
        Status.LOADING -> {
            if (isLoading!!) loading?.invoke()
        }
        Status.ERROR -> error?.invoke(Throwable(errorMessage.toString()))
    }
}
@Composable
fun EvenChanger(viewModel: CryptoListViewModel = hiltViewModel()) {
    Row {
        Button(onClick = {
            val tempList = CryptoList()
            viewModel.cryptoList.value.data.value?.forEach { tempList.add(it) }
            viewModel.cryptoList.postSuccess(tempList)
        }) { Text(text = "Success") }
        Button(onClick = {
            viewModel.cryptoList.postError("errorrrrr")
        }) { Text(text = "Error") }
        Button(onClick = {
            viewModel.cryptoList.postLoading(true)
        }) { Text(text = "Loading") }
    }

}

@Composable
fun CryptoListView(cryptos: List<CryptoListItem>?, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        if (cryptos == null) return@LazyColumn
        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}

@Composable
fun CryptoRow(navController: NavController, crypto: CryptoListItem) {
    Box {}
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary)
        .clickable {
            navController.navigate(
                "crypto_detail_screen/${crypto.currency}/${crypto.price}"
            )
        }
    ) {
        Text(
            text = crypto.currency,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = crypto.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun RetryView(
    error: String?,
    onRetry: () -> Unit
) {
    Column {
        Text(error.toString(), color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

