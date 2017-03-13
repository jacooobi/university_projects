//
// Created by Jacek Kubiak on 21/01/16.
//

#ifndef PROJEKTCPP_BASETICKET_H
#define PROJEKTCPP_BASETICKET_H

#include <string>

using namespace std;

class BaseTicket {
private:
    int ticketID;
    string startingLocation;
    string destinatedLocation;
    int createTicketID();
public:
    BaseTicket(string start="", string finish="");
    virtual string getTicketCategory() = 0;
    void setStartingLocation(string startingLocation);
    void setDestinatedLocation(string destinatedLocation);
    string getStartingLocation();
    string getDestinatedLocation();
    int getTicketID();
    string display();
    string getRoute();
};


#endif //PROJEKTCPP_BASETICKET_H
