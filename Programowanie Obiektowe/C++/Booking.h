//
// Created by Jacek Kubiak on 22/01/16.
//

#ifndef PROJEKTCPP_BOOKING_H
#define PROJEKTCPP_BOOKING_H

#include <string>
#include <chrono>
#include "BaseTicket.h"

using namespace std;

class Booking {
private:
    int bookingID;
    BaseTicket * ticket;
    string expirationDate;
    string createExpirationDate();
    int createBookingID();
public:
    Booking(BaseTicket * ticket);
    Booking(int bookingId, BaseTicket *ticket, string expirationDate);
    int getBookingID();
    BaseTicket *getTicket();
    string getExpirationDate();
    string serialize();
};


#endif //PROJEKTCPP_BOOKING_H
