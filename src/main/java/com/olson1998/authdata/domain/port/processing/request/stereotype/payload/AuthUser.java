package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

import com.olson1998.authdata.domain.port.data.stereotype.User;

import java.util.LinkedList;

public interface AuthUser extends User {

    LinkedList<Byte> getPasswordBytes();

}
