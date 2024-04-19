package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game

class Account(){
    companion object{
        private var age: Int = 95
        fun getAge(): Int{
            return age
        }
        fun setAge(age: Int) {
            this.age = age
        }
    }
}
