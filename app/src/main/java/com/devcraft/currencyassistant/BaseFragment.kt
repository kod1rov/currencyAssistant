package com.devcraft.currencyassistant

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.devcraft.currencyassistant.utils.status.OnBackPressed

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    Fragment(), OnBackPressed {

    val navController by lazy {
        val navHostFragment = (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private var _binding: T? = null
    protected val binding
        get() = _binding

    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Activity.hideKeyboard(v: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        v?.let {
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun Activity.showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun showInfoAlertDialogDismiss(msg: String?, listener: DialogInterface.OnDismissListener?) {
        if (msg == null) return
        AlertDialog.Builder(requireContext())
            .setMessage(msg)
            .setNegativeButton("Ok", null)
            .setOnDismissListener(listener)
            .create()
            .show()
    }

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) {
            return
        }
        toast.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }
}