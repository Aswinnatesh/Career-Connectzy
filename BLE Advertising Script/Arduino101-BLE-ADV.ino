#include <CurieBLE.h>

BLEPeripheral blePeripheral;  // BLE Peripheral Device
BLEService BitCampHack("19B10000-E8F2-537E-4F6C-D104768A1214"); // BLE PACKET UUID 

void setup()
{

  // set advertised local name and service UUID:
  blePeripheral.setLocalName("PAYPAL: We're Hiring");
  blePeripheral.setAdvertisedServiceUuid(BitCampHack.uuid());

  // begin advertising BLE service:
  blePeripheral.begin();
}

void loop()
{

}


