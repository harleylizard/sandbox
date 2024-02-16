package com.harleylizard.sandbox.persistent;

import java.io.DataInput;
import java.io.IOException;

public interface Readable {

    void read(DataInput dataInput) throws IOException;
}
