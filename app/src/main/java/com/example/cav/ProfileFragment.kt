package com.example.cav

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.example.cav.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var imageUri : Uri
    lateinit var firebaseStorage : FirebaseStorage
    val user = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        setup()
        recoverData()
        firebaseStorage = FirebaseStorage.getInstance()
        return view
    }


    private  fun recoverData(){
        //firebase data
        val nombre = user?.displayName
        binding.nameID.text = nombre
        val email = user?.email.toString()
        Toast.makeText(context, email, Toast.LENGTH_SHORT).show()

        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("Usuarios").document(email).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        if (it.contains("urlFoto")){
                            val urlFoto = it.getString("urlFoto")
                            if (urlFoto != "") {
                                Toast.makeText(context, urlFoto, Toast.LENGTH_SHORT).show()
                                setImage(urlFoto.toString())
                            }
                        }else{
                            Toast.makeText(context, "Selecciona una foto", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Sin registros", Toast.LENGTH_SHORT).show()
                }
        }catch (e: Exception) {
            print("no image")
        }
    }

    private fun setImage(uri: String) {
        val imageView = binding.userImage
        Picasso.get().load(uri).into(imageView)

    }

    private fun setup() {
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val shared = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val editor = shared?.edit()
            editor?.putBoolean("login", false)
            editor?.apply()

            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }



        binding.payMethods.setOnClickListener {
            val intent = Intent(context, PaymentMethodsActivity::class.java)
            startActivity(intent)
        }
        binding.editInformation.setOnClickListener {
            val intent = Intent(context, EditInfoActivity::class.java)
            startActivity(intent)
        }
        binding.myTours.setOnClickListener {
          val intent = Intent (context,MytoursActivity::class.java)
          startActivity(intent)
        }
        binding.servicio.setOnClickListener {
            val intent = Intent (context,PreguntasFrecuentesActivity::class.java)
            startActivity(intent)
        }
        binding.changeUserImage.setOnClickListener {
             val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            UploadImage(imageUri)
        }

    }

    private fun UploadImage(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = firebaseStorage.getReference("UsuariosImg/$fileName")
        storageReference.putFile(imageUri).
                addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        val fotoURL = it.toString()
                        asignarFoto(fotoURL)
                        setImage(fotoURL)
                    }.addOnFailureListener {
                        Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
                    }


                }.addOnFailureListener {
            Toast.makeText(context, "Algo salio mal shavo", Toast.LENGTH_SHORT).show()
        }


    }

    private fun asignarFoto(URL: String) {
        val firestore  = FirebaseFirestore.getInstance()
        val email = user?.email
        firestore.collection("Usuarios").document(email.toString()).get()
            .addOnSuccessListener {
                if (it.exists()){
                    if (it.contains("urlFoto")){
                        firestore.collection("Usuarios").document(email.toString())
                            .update("urlFoto",URL)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "La imagen se actualizo correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Error al actualizar imagen",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }else{
                        val newData = hashMapOf("urlFoto" to URL)
                        firestore.collection("Usuarios").document(email.toString())
                            .set(newData, SetOptions.merge())
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Campo creado y asignado exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener{
                                Toast.makeText(
                                    context,
                                    "No se creo ni se asigno exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }else{
                    Toast.makeText(context, "No existen datos", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Algo salio mal desde el primer momento",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}