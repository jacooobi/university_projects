//
// Created by Jacek Kubiak on 22/01/16.
//

#ifndef PROJEKTCPP_BOOKINGASSISTANT_H
#define PROJEKTCPP_BOOKINGASSISTANT_H

#include <string>
#include <iostream>
#include "ReservationBase.h"
#include "BaseTicket.h"
#include "Booking.h"

using namespace std;

class BookingAssistant {
private:
    string filename;
    ReservationBase<BaseTicket, Booking> *database;
    void initDB();
    void initialGreeting();
public:
    BookingAssistant(string filename);
    void call();
};


#endif //PROJEKTCPP_BOOKINGASSISTANT_H
