package com.choice.listonview.di.model

data class Ticket(
    val id: Int = -1,
    val title: String,
    val description: String,
    val date: String,
    val isDownloaded: Boolean = false,
    val isChanged: Boolean = false
){

    companion object {
        val EXAMPLE_LIST = listOf(
            Ticket(1, "Ticket 1", "This is the first ticket.", "01/08/2023", true),
            Ticket(2, "Ticket 2", "This is the second ticket.", "02/08/2023"),
            Ticket(3, "Ticket 3", "This is the third ticket.", "03/08/2023", true),
            Ticket(4, "Ticket 4", "This is the fourth ticket.", "04/08/2023"),
            Ticket(5, "Ticket 5", "This is the fifth ticket.", "05/08/2023"),
            Ticket(6, "Ticket 6", "This is the sixth ticket.", "06/08/2023"),
            Ticket(7, "Ticket 7", "This is the seventh ticket.", "07/08/2023", true),
            Ticket(8, "Ticket 8", "This is the eighth ticket.", "08/08/2023"),
            Ticket(9, "Ticket 9", "This is the ninth ticket.", "09/08/2023"),
            Ticket(10, "Ticket 10", "This is the tenth ticket.", "10/08/2023", true),
            Ticket(11, "Ticket 11", "This is the eleventh ticket.", "11/08/2023"),
            Ticket(12, "Ticket 12", "This is the twelfth ticket.", "12/08/2023"),
            Ticket(13, "Ticket 13", "This is the thirteenth ticket.", "13/08/2023"),
            Ticket(14, "Ticket 14", "This is the fourteenth ticket.", "14/08/2023", true),
            Ticket(15, "Ticket 15", "This is the fifteenth ticket.", "15/08/2023"),
            Ticket(16, "Ticket 16", "This is the sixteenth ticket.", "16/08/2023"),
            Ticket(17, "Ticket 17", "This is the seventeenth ticket.", "17/08/2023"),
            Ticket(18, "Ticket 18", "This is the eighteenth ticket.", "18/08/2023", true),
            Ticket(19, "Ticket 19", "This is the nineteenth ticket.", "19/08/2023"),
            Ticket(20, "Ticket 20", "This is the twentieth ticket.", "20/08/2023")
        )

    }

}