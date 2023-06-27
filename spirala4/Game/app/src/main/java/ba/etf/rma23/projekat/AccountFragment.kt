package ba.etf.rma23.projekat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ba.etf.rma23.data.repositories.GameReviewListViewModel
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository

class AccountFragment : Fragment() {
    private lateinit var saveButton : Button
    private lateinit var ageText: EditText
    private lateinit var deleteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_account, container, false)
        saveButton = view.findViewById(R.id.saveButton)
        ageText = view.findViewById(R.id.age)
        deleteButton = view.findViewById(R.id.deleteButton)

        saveButton.setOnClickListener{
            AccountGamesRepository.setAge(ageText.text.toString().toInt())
            context?.let { it1 -> Functions.toastSuccess(it1) }
        }

        deleteButton.setOnClickListener{
            GameReviewListViewModel().deleteAllReviews(onSuccess = { response->
                val toast = Toast.makeText(context, "${response.deleted} reviews have been deleted", Toast.LENGTH_SHORT)
                toast.show()
            }, onError = {
                context?.let { it1 -> Functions.toastError(it1) }
            })
        }
        return view
    }

}