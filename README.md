# UtilityContacts

App to store contact details of Tradesman (plumber, electrician etc.) locationwise and allow communication with them via phone call.

## App Tech terms : 
- Kotlin
- Firebase DB (server side)
- Room DB (app side)
- MVVM

## Sample Json to be added to server 
{
    "base": [
        {
            "name": "Deepak",
            "phone": "9392949291",
            "occ": "occ2",
            "loc": "Halwani",
            "altPhone": "929919111",
            "address": "Some address",
            "from": "10 a.m.",
            "to": "8 p.m."
        },
        {
            "name": "Raman",
            "phone": "9392949291",
            "occ": "occ1",
            "altPhone": "929919111",
            "loc": "Kathgodam",
            "address": "Some address",
            "from": "10 a.m.",
            "to": "8 p.m."
        },
        ...
    ]
}
