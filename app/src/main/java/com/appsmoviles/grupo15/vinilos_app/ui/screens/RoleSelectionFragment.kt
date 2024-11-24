package com.appsmoviles.grupo15.vinilos_app.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appsmoviles.grupo15.vinilos_app.R
import com.appsmoviles.grupo15.vinilos_app.ui.MainActivity

class RoleSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.role_selection_fragment, container, false)

        view.findViewById<Button>(R.id.button_usuario).setOnClickListener {
            navigateToAlbumFragment("Usuario")
        }

        view.findViewById<Button>(R.id.button_coleccionista).setOnClickListener {
            navigateToAlbumFragment("Coleccionista")
        }

        return view
    }

    private fun navigateToAlbumFragment(selectedRole: String) {
        val sharedPref = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("selected_role", selectedRole)
            apply()
        }

        (activity as? MainActivity)?.updateRoleHeader()

        findNavController().navigate(R.id.albumFragment)
    }

}