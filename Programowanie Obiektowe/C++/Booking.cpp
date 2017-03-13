//
// Created by Jacek Kubiak on 22/01/16.
//

#include <iostream>
#include "Booking.h"
using namespace std::chrono;

int Booking::createBookingID() {
    int ms = duration_cast< milliseconds >(system_clock::now().time_since_epoch()).count();
    usleep(10000);
    return ms;
}

string Booking::createExpirationDate() {
    srand (time(NULL));

    time_t now = time(0);
    tm *ltm = localtime(&now);
    char buffer [20];

    ltm->tm_mon = rand() % 12;
    ltm->tm_mday = rand() % 28;

    strftime (buffer,80, "%m/%d/%Y", ltm);
    return string(buffer);
}

Booking::Booking(BaseTicket *ticket) {
    this->bookingID = createBookingID();
    this->ticket = ticket;
    this->expirationDate = createExpirationDate();
}

Booking::Booking(int bookingId, BaseTicket *ticket, string expirationDate) {
    this->bookingID = createBookingID();
    this->ticket = ticket;
    this->expirationDate = createExpirationDate();
}

int Booking::getBookingID() {
    return bookingID;
}

string Booking::getExpirationDate() {
    return expirationDate;
}

BaseTicket* Booking::getTicket() {
    return ticket;
}

string Booking::serialize() {
    return to_string(bookingID) + " " + to_string(ticket->getTicketID()) + " " + expirationDate;
}