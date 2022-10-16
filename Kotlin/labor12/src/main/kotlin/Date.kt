import java.time.Month
import java.time.MonthDay
import java.time.Year

data class Date(val y: Year, val m: Month, val d: MonthDay){

    fun isLeapYear (year: Year): Boolean{
        return year.isLeap
    }

}


