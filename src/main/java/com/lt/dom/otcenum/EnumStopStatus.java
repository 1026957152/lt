package com.lt.dom.otcenum;

public enum EnumStopStatus {

    Open,// - A stop that has not yet been reached.

    Arrived,// - The vehicle has reached the stop location.

            Completed,// - The vehicle has left the stop location. A setting via API Key is available to automatically trigger Arrived and Completed if the vehicle enters or exits the location’s Site or a .5 mile geofence of the location. Without that setting, the status can be updated manually using the Update StopStatus API.

            Canceled,// - The stop has been canceled.

            NotViable,// -
    ;


}
