package com.example.sdksamples;

import com.impinj.octane.AntennaConfig;
import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;

import java.util.List;
import java.util.Scanner;
import java.util.PrimitiveIterator.OfDouble;

import org.llrp.ltk.generated.custom.enumerations.ImpinjIntelligentAntennaMode;
import org.llrp.ltk.generated.custom.enumerations.ImpinjInventorySearchType;
import org.llrp.ltk.generated.custom.enumerations.ImpinjLowDutyCycleMode;
import org.llrp.ltk.generated.custom.parameters.ImpinjIntelligentAntennaManagement;
import org.llrp.ltk.generated.custom.parameters.ImpinjInventorySearchMode;
import org.llrp.ltk.generated.custom.parameters.ImpinjLowDutyCycle;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.SET_READER_CONFIG;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AntennaConfiguration;
import org.llrp.ltk.generated.parameters.C1G2InventoryCommand;
import org.llrp.ltk.generated.parameters.C1G2RFControl;
import org.llrp.ltk.generated.parameters.C1G2SingulationControl;
import org.llrp.ltk.generated.parameters.Custom;
import org.llrp.ltk.generated.parameters.InventoryParameterSpec;
import org.llrp.ltk.generated.parameters.RFReceiver;
import org.llrp.ltk.generated.parameters.RFTransmitter;
import org.llrp.ltk.generated.parameters.ROReportSpec;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.types.Bit;
import org.llrp.ltk.types.TwoBitField;
import org.llrp.ltk.types.UnsignedInteger;
import org.llrp.ltk.types.UnsignedShort;


public class IntelligentAntennaManagement {

    public static void main(String[] args) {

        try {
            String hostname = "192.168.25.220";//System.getProperty(SampleProperties.hostname);

            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + SampleProperties.hostname + "' property");
            }
            
            ImpinjReader reader = new ImpinjReader();
            Settings settings = reader.queryDefaultSettings();

            ADD_ROSPEC AddROSpecMessage = reader.buildAddROSpecMessage(settings);
            SET_READER_CONFIG SetReaderConfigMessage = reader.buildSetReaderConfigMessage(settings);

            C1G2InventoryCommand c1g2Inv = new C1G2InventoryCommand();
            c1g2Inv.setTagInventoryStateAware(new Bit(0));
            ImpinjIntelligentAntennaManagement iIntelliAntMgmt = new ImpinjIntelligentAntennaManagement();
            iIntelliAntMgmt.setManagementEnabled(new ImpinjIntelligentAntennaMode(ImpinjIntelligentAntennaMode.Enabled));

            c1g2Inv.getCustomList().add(iIntelliAntMgmt);

            reader.applySettings(SetReaderConfigMessage, AddROSpecMessage);

//            ImpinjReader reader = new ImpinjReader();
//            AntennaConfigGroup antennas;
//
//            // Connect
//            System.out.println("Connecting to " + hostname);
//            reader.connect(hostname);
//
//            // Get the default settings
//            Settings settings = reader.queryDefaultSettings();
//            
//            ADD_ROSPEC addRospec = reader.buildAddROSpecMessage(settings);
//            SET_READER_CONFIG setReaderConfig = reader.buildSetReaderConfigMessage(settings);
//  
//            ROSpec rOSpec= addRospec.getROSpec();
//            AISpec aiSpec = (AISpec) rOSpec.getSpecParameterList().get(0);
//            InventoryParameterSpec inventoryParameterSpec = aiSpec.getInventoryParameterSpecList().get(0);
//            //inventoryParameterSpec.addToAntennaConfigurationList(antennaConfiguration);
//			
//            
//            for (int i = 0; i <inventoryParameterSpec.getAntennaConfigurationList().size(); i++) {
//				AntennaConfiguration antennaConfiguration = 
//							inventoryParameterSpec.getAntennaConfigurationList().get(i);
//				
//				// RFReceiver
//				RFReceiver rFReceiver = antennaConfiguration.getRFReceiver();
//				rFReceiver.setReceiverSensitivity(new UnsignedShort(1));
//				antennaConfiguration.setRFReceiver(rFReceiver);
//				
//				// RFTransmitter
//				RFTransmitter rFTransmitter = antennaConfiguration.getRFTransmitter();
//				rFTransmitter.setTransmitPower(new UnsignedShort(61));
//				rFTransmitter.setChannelIndex(new UnsignedShort(1));
//				rFTransmitter.setHopTableID(new UnsignedShort(1));
//				antennaConfiguration.setRFTransmitter(rFTransmitter);
//				
//				// C1G2InventoryCommand
//				C1G2InventoryCommand c1G2InventoryCommand = (C1G2InventoryCommand) 
//						antennaConfiguration.getAirProtocolInventoryCommandSettingsList().get(0);
//				c1G2InventoryCommand.setTagInventoryStateAware(new Bit(0));
//				
//				// C1G2InventoryCommand/C1G2RFControl
//				C1G2RFControl c1G2RFControl = c1G2InventoryCommand.getC1G2RFControl();
//				c1G2RFControl.setModeIndex(new UnsignedShort(3));
//				c1G2RFControl.setTari(new UnsignedShort(0));
//				c1G2InventoryCommand.setC1G2RFControl(c1G2RFControl);
//				
//				// C1G2InventoryCommand/C1G2SingulationControl
//				C1G2SingulationControl c1G2SingulationControl = c1G2InventoryCommand.getC1G2SingulationControl();
//				Bit[] dataBits = new Bit[2];
//				dataBits[0] = new Bit(0);
//				dataBits[1] = new Bit(1);
//				c1G2SingulationControl.setSession(new TwoBitField(dataBits));
//				c1G2SingulationControl.setTagPopulation(new UnsignedShort(2));
//				c1G2SingulationControl.setTagTransitTime(new UnsignedInteger(0));
//				c1G2InventoryCommand.setC1G2SingulationControl(c1G2SingulationControl);
//				
//				
//				List<Custom> customs = c1G2InventoryCommand.getCustomList();
//				customs.clear();
//				
//				ImpinjInventorySearchMode impinjInventorySearchMode = new ImpinjInventorySearchMode();
//				impinjInventorySearchMode.setInventorySearchMode(new ImpinjInventorySearchType(ImpinjInventorySearchType.Single_Target));
//				customs.add(impinjInventorySearchMode);
//				
//				ImpinjLowDutyCycle impinjLowDutyCycle = new ImpinjLowDutyCycle();
//				impinjLowDutyCycle.setLowDutyCycleMode(new ImpinjLowDutyCycleMode(ImpinjLowDutyCycleMode.Disabled));
//				impinjLowDutyCycle.setEmptyFieldTimeout(new UnsignedShort(10000));
//				impinjLowDutyCycle.setFieldPingInterval(new UnsignedShort(200));
//				customs.add(impinjLowDutyCycle);
//				
//				ImpinjIntelligentAntennaManagement impinjIntelligentAntennaManagement = new ImpinjIntelligentAntennaManagement();
//				impinjIntelligentAntennaManagement.setManagementEnabled(new ImpinjIntelligentAntennaMode(1));
//				
//				customs.add(impinjIntelligentAntennaManagement);
//            }
//            reader.applySettings(setReaderConfig,addRospec);
            

            // Apply the new settings
            //reader.applySettings(settings);

            // connect a listener
            reader.setTagReportListener(new TagReportListenerImplementation());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
