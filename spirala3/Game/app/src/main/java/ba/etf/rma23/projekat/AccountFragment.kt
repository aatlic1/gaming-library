package ba.etf.rma23.projekat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ba.etf.rma23.projekat.data.repositories.Account
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository

class AccountFragment : Fragment() {
    private lateinit var saveButton : Button
    private lateinit var ageText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_account, container, false)
        saveButton = view.findViewById(R.id.saveButton)
        ageText = view.findViewById(R.id.age)

        saveButton.setOnClickListener{
            AccountGamesRepository.setAge(ageText.text.toString().toInt())
            val toast = Toast.makeText(context, "The year was set succeefully.", Toast.LENGTH_SHORT)
            toast.show()
        }
        return view
    }

}