//
// Created by Jacek Kubiak on 21/01/16.
//

#include "BaseTicket.h"
#include <chrono>

using namespace std::chrono;

int BaseTicket::createTicketID() {
    int ms = duration_cast< milliseconds >(system_clock::now().time_since_epoch()).count();
    usleep(10000);
    return ms;
}

BaseTicket::BaseTicket(string start, string finish) {
    this->ticketID = createTicketID();

    this->startingLocation = start;
    this->destinatedLocation = finish;
}

string BaseTicket::getStartingLocation() {
    return startingLocation;
}

string BaseTicket::getDestinatedLocation() {
    return destinatedLocation;
}

int BaseTicket::getTicketID() {
    return ticketID;
}

string BaseTicket::display() {
    return string(getTicketCategory()) + " " + getStartingLocation() + "-" + getDestinatedLocation();
}

string BaseTicket::getRoute() {
    return getStartingLocation() + "-" + getDestinatedLocation();
}

void BaseTicket::setStartingLocation(string startingLocation) {
    this->startingLocation = startingLocation;
}

void BaseTicket::setDestinatedLocation(string destinatedLocation) {
    this->destinatedLocation = destinatedLocation;
}