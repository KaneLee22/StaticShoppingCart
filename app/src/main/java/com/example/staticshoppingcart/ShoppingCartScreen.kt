package com.example.staticshoppingcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import kotlinx.coroutines.launch

data class Goods(  // Use class to represent different goods
    val imageRes: Int,
    val name: String,
    val price: Int,
    val quantity: Int
)

// Hard Code  Shopping list
val goodsList = listOf(
    Goods(
        imageRes = R.drawable.goods_photo1,
        name = "Elden Ring Shadow of the Erdtree PS5",
        price = 69,
        quantity = 1
    ),
    Goods(
        imageRes = R.drawable.goods_photo2,
        name = "NACON Revolution 5 Pro Officially",
        price = 199,
        quantity = 1
    ),
    Goods(
        imageRes = R.drawable.goods_photo3,
        name = "Hasselblad X2D",
        price = 8199,
        quantity = 1
    ),
    Goods(
        imageRes = R.drawable.goods_photo4,
        name = "2024 MacBook Air",
        price = 1089,
        quantity = 1
    )
)

// shopping cart screen
@OptIn(ExperimentalMaterial3Api::class) // by 4o
@Composable
fun ShoppingCartScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val totalPrice =
        goodsList.sumOf { it.price * it.quantity } // didn't consider the quantity, 4o helps to improve it
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shopping Cart",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }, // improve the UI by 4o
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant) // A better background
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // goods list
            goodsList.forEach { goods ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // goods image
                        Image(
                            painter = painterResource(id = goods.imageRes),
                            contentDescription = goods.name,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        // goods name price and quantity, generate by 4o
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = goods.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$${goods.price} x ${goods.quantity}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    // Divider
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: $$totalPrice",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    // Checkout button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Ordered",
                                    withDismissAction = true
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Checkout",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
