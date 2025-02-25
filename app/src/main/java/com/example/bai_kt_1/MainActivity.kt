package com.example.bai_kt_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bai_kt_1.ui.theme.Bai_KT_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_KT_1Theme {
                var selectedProduct by remember { mutableStateOf<Product?>(null) }
                if (selectedProduct == null) {
                    ProductListScreen(onProductClick = { selectedProduct = it })
                } else {
                    ProductDetailsScreen(product = selectedProduct, onBack = { selectedProduct = null })
                }
            }
        }
    }
}

data class Product(val id: Int, val name: String, val price: String, val image: Painter, val rating: String, val description: String)

@Composable
fun getSampleProducts(): List<Product> {
    return listOf(
        Product(1, "Product 1", "$10.00", painterResource(id = R.drawable.c7db377b177fc8e2ff75a769022dcc23), "★★★★☆", "This is a great product 1."),
        Product(2, "Product 2", "$20.00", painterResource(id = R.drawable.c7db377b177fc8e2ff75a769022dcc23), "★★★☆☆", "This is a great product 2."),
        Product(3, "Product 3", "$30.00", painterResource(id = R.drawable.c7db377b177fc8e2ff75a769022dcc23), "★★★★★", "This is a great product 3."),
        Product(4, "Product 4", "$40.00", painterResource(id = R.drawable.c7db377b177fc8e2ff75a769022dcc23 ), "★★☆☆☆", "This is a great product 4.")
    )
}

@Composable
fun ProductListScreen(onProductClick: (Product) -> Unit) {
    val products = getSampleProducts()
    LazyColumn {
        items(products) { product ->
            ProductItem(product = product, onClick = { onProductClick(product) })
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(painter = product.image, contentDescription = null, modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = product.rating, style = MaterialTheme.typography.bodyMedium)
            Text(text = product.price, style = MaterialTheme.typography.bodyMedium)
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ProductDetailsScreen(product: Product?, onBack: () -> Unit) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    product?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(painter = product.image, contentDescription = null, modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = product.rating, style = MaterialTheme.typography.bodyMedium)
            Text(text = product.price, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Handle add to cart */ }) {
                Text("Thêm vào giỏ hàng")
            }
        }
    }
    LaunchedEffect(Unit) {
        backDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductList() {
    ProductListScreen(onProductClick = {})
}
