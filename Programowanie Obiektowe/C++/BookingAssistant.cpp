//
// Created by Jacek Kubiak on 22/01/16.
//

#include "BookingAssistant.h"
#include "PlaneTicket.h"
#include "ShipTicket.h"
#include "JoinedTicket.h"

BookingAssistant::BookingAssistant(string filename) {
    this->filename = filename;
    this->database = new ReservationBase<BaseTicket, Booking>(filename);
    initDB();
    this->database->loadReservations();
}

void BookingAssistant::initDB() {
    BaseTicket *ticket1 = new PlaneTicket("VIP","Poznan", "Warszawa");
    BaseTicket *ticket2 = new PlaneTicket("VIP","Berlin", "Londyn");
    BaseTicket *ticket3 = new PlaneTicket("VIP","Paryz", "Nowy Jork");
    BaseTicket *ticket4 = new PlaneTicket("VIP","Szanghai", "Bruksela");
    BaseTicket *ticket5 = new PlaneTicket("VIP","Barcelona", "Moskwa");

    BaseTicket *ticket6 = new ShipTicket(false, "Krakow", "Poznan");
    BaseTicket *ticket7 = new ShipTicket(false, "Krakow", "Warszawa");
    BaseTicket *ticket8 = new ShipTicket(false, "Szczecin", "Świnoujscie");
    BaseTicket *ticket9 = new ShipTicket(false, "Gdansk", "Gdynia");
    BaseTicket *ticket10 = new ShipTicket(false, "Krakow", "Budapeszt");

    vector<BaseTicket*> tickets;
    tickets.push_back(ticket1);
    tickets.push_back(ticket2);
    tickets.push_back(ticket6);
    BaseTicket *ticket11 = new JoinedTicket(tickets);

    vector<BaseTicket*> tickets2;
    tickets2.push_back(ticket10);
    tickets2.push_back(ticket2);
    tickets2.push_back(ticket8);
    BaseTicket *ticket12 = new JoinedTicket(tickets2);

    this->database->addNewAvailableTicket(ticket1);
    this->database->addNewAvailableTicket(ticket2);
    this->database->addNewAvailableTicket(ticket3);
    this->database->addNewAvailableTicket(ticket4);
    this->database->addNewAvailableTicket(ticket5);

    this->database->addNewAvailableTicket(ticket6);
    this->database->addNewAvailableTicket(ticket7);
    this->database->addNewAvailableTicket(ticket8);
    this->database->addNewAvailableTicket(ticket9);
    this->database->addNewAvailableTicket(ticket10);
    this->database->addNewAvailableTicket(ticket11);
    this->database->addNewAvailableTicket(ticket12);
}

void BookingAssistant::call() {
    int selection;

    initialGreeting();
    while(1) {

        cin >> selection;
        if (selection == 0) break;

        switch(selection) {
            case 1:
                int number;
                cout << "Oto dostepne bilety:" << endl;
                database->listAvailableTickets();
                cout << "Wprowadz numer biletu, aby dokonac rezerwacji" << endl;
                cin >> number;

                (*database) += new Booking(database->fetch(number));

                cout << "Dodano !" << endl;
                initialGreeting();
                break;

            case 2:
                int number2;
                cout << "Podaj numer rezerwacji, z której chcesz zrezygnować" << endl;
                cin >> number2;

                if (!database->exists(number2)) {
                    cout << "Podana rezerwacja nie istnieje! \n";
                    initialGreeting();
                    break;
                }

                (*database) -= number2;
                cout << "Rezerwacja odwołana!\n";
                initialGreeting();
                break;

            case 3:
                database->listReservations();
                initialGreeting();
                break;

            default:
                cout << "Błędna opcja! Wcisnij 0 zeby wyjść." << endl;
                initialGreeting();
                break;
        }
    }
}

void BookingAssistant::initialGreeting() {
    cout << "--------------------------------------" << endl;
    cout << "Cześć! Co chcesz zrobić?" << endl;
    cout << "1) Zarezerwować bilet" << endl;
    cout << "2) Anulować rezerwację" << endl;
    cout << "3) Wyświetlić wszystkie rezerwacje" << endl;
    cout << "Wpisz 0 żeby wyjść" << endl;
    cout << "--------------------------------------" << endl;
}