package com.appsmoviles.grupo15.vinilos_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsmoviles.grupo15.vinilos_app.R

@Composable
fun RoleSelectionScreen(onRoleSelected: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a Vinilos",
                fontSize = 24.sp,
                color = colorResource(id = R.color.primaryGray),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Accede como:",
                fontSize = 20.sp,
                color = colorResource(id = R.color.textSecondary),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRoleSelected("Usuario") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primaryGray),
                    contentColor = colorResource(id = R.color.textPrimary)
                )
            ) {
                Text(text = "Usuario", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRoleSelected("Coleccionista") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.secondaryGray),
                    contentColor = colorResource(id = R.color.textPrimary)
                )
            ) {
                Text(text = "Coleccionista", fontSize = 18.sp)
            }
        }
    }
}
