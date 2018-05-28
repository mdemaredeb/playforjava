package utils;

import play.api.libs.iteratee.Iteratee;
import play.api.mvc.EssentialAction;
import play.api.mvc.RequestHeader;
import play.api.mvc.Result;
import play.api.mvc.SimpleResult;
import scala.runtime.AbstractFunction1;

public abstract class JavaEssentialAction extends AbstractFunction1<RequestHeader,Iteratee<byte[],SimpleResult>> implements EssentialAction {

}/*
public abstract class JavaEssentialAction extends
        AbstractFunction1<RequestHeader, Iteratee<byte[], Result>>
        implements EssentialAction{}*/