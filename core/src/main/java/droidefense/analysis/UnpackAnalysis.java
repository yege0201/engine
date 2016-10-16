package droidefense.analysis;

import apkr.external.modules.helpers.log4j.Log;
import apkr.external.modules.helpers.log4j.LoggerType;
import droidefense.analysis.base.AbstractAndroidAnalysis;
import droidefense.mod.vfs.model.base.IVirtualNode;

import java.util.ArrayList;

/**
 * Created by sergio on 16/2/16.
 */
public final class UnpackAnalysis extends AbstractAndroidAnalysis {

    private transient ArrayList<IVirtualNode> files;

    public UnpackAnalysis() {
    }

    @Override
    public boolean analyze() {
        Log.write(LoggerType.TRACE, "Unpacking .apk...");
        //unpack file
        files = apkFile.unpackWithTechnique(currentProject, apkFile);
        if (files != null && files.size() > 0) {
            //save files count
            positiveMatch = files.size() > 0;
            //SET APP FILES & CALCULATE THEIR HASHES, FUZZING HASH, EXTENSION, SIGNATURES
            apkFile.decodeWithTechnique(currentProject, files);
            return true;
        }
        //currentProject.setAppFiles(files);
        timeStamp.stop();
        return false;
    }

    @Override
    public String getName() {
        return "Android .apk unpacker";
    }
}