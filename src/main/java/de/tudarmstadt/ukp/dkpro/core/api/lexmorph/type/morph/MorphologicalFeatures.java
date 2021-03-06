

/* First created by JCasGen Fri Dec 14 23:20:03 CET 2018 */
package de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Jan 07 22:47:36 CET 2019
 * XML source: C:/Users/Jonas/Downloads/de.unidue.langtech.bachelor.meise/de.unidue.langtech.bachelor.meise/typesystem.xml
 * @generated */
public class MorphologicalFeatures extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MorphologicalFeatures.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected MorphologicalFeatures() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MorphologicalFeatures(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MorphologicalFeatures(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MorphologicalFeatures(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: gender

  /** getter for gender - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGender() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_gender);}
    
  /** setter for gender - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGender(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_gender, v);}    
   
    
  //*--------------*
  //* Feature: number

  /** getter for number - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNumber() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNumber(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_number, v);}    
   
    
  //*--------------*
  //* Feature: case

  /** getter for case - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCase() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_case);}
    
  /** setter for case - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCase(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_case, v);}    
   
    
  //*--------------*
  //* Feature: degree

  /** getter for degree - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDegree() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_degree);}
    
  /** setter for degree - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDegree(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_degree, v);}    
   
    
  //*--------------*
  //* Feature: transitivity

  /** getter for transitivity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTransitivity() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_transitivity == null)
      jcasType.jcas.throwFeatMissing("transitivity", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_transitivity);}
    
  /** setter for transitivity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTransitivity(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_transitivity == null)
      jcasType.jcas.throwFeatMissing("transitivity", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_transitivity, v);}    
   
    
  //*--------------*
  //* Feature: tense

  /** getter for tense - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTense() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: mood

  /** getter for mood - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMood() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_mood == null)
      jcasType.jcas.throwFeatMissing("mood", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_mood);}
    
  /** setter for mood - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMood(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_mood == null)
      jcasType.jcas.throwFeatMissing("mood", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_mood, v);}    
   
    
  //*--------------*
  //* Feature: voice

  /** getter for voice - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVoice() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_voice == null)
      jcasType.jcas.throwFeatMissing("voice", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_voice);}
    
  /** setter for voice - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVoice(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_voice == null)
      jcasType.jcas.throwFeatMissing("voice", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_voice, v);}    
   
    
  //*--------------*
  //* Feature: definiteness

  /** getter for definiteness - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDefiniteness() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_definiteness == null)
      jcasType.jcas.throwFeatMissing("definiteness", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_definiteness);}
    
  /** setter for definiteness - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDefiniteness(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_definiteness == null)
      jcasType.jcas.throwFeatMissing("definiteness", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_definiteness, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets 
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_value, v);}    
   
    
  //*--------------*
  //* Feature: person

  /** getter for person - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPerson() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_person);}
    
  /** setter for person - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPerson(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_person, v);}    
   
    
  //*--------------*
  //* Feature: aspect

  /** getter for aspect - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAspect() {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_aspect == null)
      jcasType.jcas.throwFeatMissing("aspect", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_aspect);}
    
  /** setter for aspect - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAspect(String v) {
    if (MorphologicalFeatures_Type.featOkTst && ((MorphologicalFeatures_Type)jcasType).casFeat_aspect == null)
      jcasType.jcas.throwFeatMissing("aspect", "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalFeatures_Type)jcasType).casFeatCode_aspect, v);}    
  }

    