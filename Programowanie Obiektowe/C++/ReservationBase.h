//
// Created by Jacek Kubiak on 21/01/16.
//

#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include "Booking.h"

using namespace std;

template <typename T, typename D>
class ReservationBase {
private:
    string filename;
    vector<T*> availableTickets;
    vector<D*> bookings;
    void updateFile();
    vector<string> *split(string s, char delim);
public:
    ReservationBase(string filename) : filename(filename) { }
    void addNewAvailableTicket(T *ticket);
    void addNewReservation(D *booking);
    void listAvailableTickets();
    void listReservations();
    T* fetch(int index);
    int getTicketPosition(int ticketID);
    bool exists(int reservationID);
    void operator+=(D* booking);
    void operator-=(int reservationID);
    void loadReservations();
};

template <typename T, typename D>
void ReservationBase<T, D>::addNewAvailableTicket(T *ticket) {
    this->availableTickets.push_back(ticket);
}

template <typename T, typename D>
void ReservationBase<T, D>::addNewReservation(D *booking) {
    this->bookings.push_back(booking);
}

template <typename T, typename D>
void ReservationBase<T, D>::listAvailableTickets() {
    for(typename vector<T*>::iterator it = availableTickets.begin(); it != availableTickets.end(); ++it) {
        cout << it - availableTickets.begin() << ") " << (*it)->display() << endl;
    }
}

template <typename T, typename D>
void ReservationBase<T, D>::listReservations() {
    if (bookings.size()==0) cout << "Brak rezerwacji \n";

    for(typename vector<D*>::iterator it = bookings.begin(); it != bookings.end(); ++it) {
        cout << "Numer rezerwacji: " << (*it)->getBookingID() << " " << (*it)->getTicket()->getTicketCategory() << " "
             << (*it)->getTicket()->getRoute() << ". Ważny do " << (*it)->getExpirationDate() << endl;
    }
}

template <typename T, typename D>
bool ReservationBase<T, D>::exists(int reservationID) {
    for(typename vector<D*>::iterator it = bookings.begin(); it != bookings.end(); ++it) {
        if ((*it)->getBookingID() == reservationID) return true;
    }

    return false;
}

template <typename T, typename D>
T* ReservationBase<T, D>::fetch(int index) {
    return availableTickets.at(index);
}

template <typename T, typename D>
int ReservationBase<T, D>::getTicketPosition(int ticketID) {
    int position = 0;

    for(typename vector<T*>::iterator it = availableTickets.begin(); it != availableTickets.end(); ++it) {
        if ((*it)->getTicketID() == ticketID) position = std::distance(availableTickets.begin(), it);
    }

    return position;
}


template <typename T, typename D>
void ReservationBase<T, D>::operator+=(D* ticket) {
    bookings.push_back(ticket);
    updateFile();
}

template <typename T, typename D>
void ReservationBase<T, D>::operator-=(int reservationID) {
    int position = 0;

    for(typename vector<D*>::iterator it = bookings.begin(); it != bookings.end(); ++it) {
        if ((*it)->getBookingID() == reservationID) position = std::distance(bookings.begin(), it);
    }

    bookings.erase(bookings.begin() + position);
    updateFile();
}

template <typename T, typename D>
void ReservationBase<T, D>::updateFile() {
    ofstream myfile;
    myfile.open (filename, ios::trunc);

    for(typename vector<D*>::iterator it = bookings.begin(); it != bookings.end(); ++it) {
        myfile << (*it)->serialize() << endl;
    }

    myfile.close();
}

template <typename T, typename D>
void ReservationBase<T, D>::loadReservations() {
    ifstream file(filename);
    string line;

    if (!file) {
        cout << "Nie znaleziono pliku! \n";
        return;
    } else {
        while (getline(file, line)) {
            vector<string> *elements = split(line, ' ');
            int ticketIndex = getTicketPosition(stoi(elements->at(1)));
            if (ticketIndex < 0) {
                cout << "Błędne dane! \n";
                return;
            }

            bookings.push_back(new Booking(stoi(elements->at(0)), fetch(ticketIndex), elements->at(2)));
        }
        file.close();
    }
}

template <typename T, typename D>
vector<string> * ReservationBase<T, D>::split(string s, char delimiter) {
    vector<string> *toReturn = new vector<string>();
    stringstream ss(s);
    string item;

    while (getline(ss, item, delimiter)) {
        toReturn->push_back(item);
    }

    return toReturn;
}


