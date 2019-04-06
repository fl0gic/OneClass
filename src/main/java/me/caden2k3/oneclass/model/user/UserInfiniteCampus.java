package me.caden2k3.oneclass.model.user;

import lombok.Data;
import me.caden2k3.infinitecampusapi.InfiniteCampusAPI;
import me.caden2k3.infinitecampusapi.Student;
import me.caden2k3.infinitecampusapi.exception.InvalidCredentialsException;
import me.caden2k3.oneclass.OneClass;
import nu.xom.ParsingException;

import java.io.IOException;

/**
 * @author Caden Kriese
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public @Data class UserInfiniteCampus {
    public String username;
    public String password;
    public String districtId;

    public UserInfiniteCampus(String username, String password, String districtId) {
        this.username = username;
        this.password = password;
        this.districtId = districtId;
    }

    public Student toStudent() throws ParsingException, IOException, InvalidCredentialsException {
        if (OneClass.getInstance().getInfiniteCampusCore() == null)
            OneClass.getInstance().setInfiniteCampusCore(new InfiniteCampusAPI(districtId));

        return OneClass.getInstance().getInfiniteCampusCore().getStudent(username, password);
    }
}
