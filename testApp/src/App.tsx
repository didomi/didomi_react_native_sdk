import React, { useState } from 'react';

import { SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Didomi, DidomiEventType } from '@didomi/react-native';
import Methods from './Methods';
import Getters from './Getters';
import GettersParams from './GettersParams';
import Setters from './Setters';
import { TestEvent } from './Types';
import { DidomiListener } from '../../src/DidomiListener';

function App() {
  const [receivedEvent, setReceivedEvent] = useState<TestEvent>({
    name: 'NONE',
  });

  const [sdkStatus, setSdkStatus] = useState("NOT_READY");

  const registerListener = (eventType: DidomiEventType) => {
    Didomi.addEventListener(eventType, (data: any) => {
      setReceivedEvent({ name: eventType, data });
      console.log('event received: ' + eventType);
    });
  };

  // For some reason listeners added in Didomi.initialize against the NativeEventEmitter are kept between test runs.
  // This is not the same as the listeners added by app devs.
  // In order to make tests run smoother locally, we remove them each time.
  // In theory this shouldn't be an issue on a regular app.
  const removeAllNativeListeners = () => {
    Object.values(DidomiEventType).forEach((eventTypeValue) => {
      DidomiListener.eventEmitter.removeAllListeners(eventTypeValue);
    });
  };

  React.useEffect(() => {
    removeAllNativeListeners();
    Didomi.removeAllEventListeners();

    registerListener(DidomiEventType.CONSENT_CHANGED);
    registerListener(DidomiEventType.ERROR);
    registerListener(DidomiEventType.HIDE_NOTICE);
    registerListener(DidomiEventType.NOTICE_CLICK_AGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_DISAGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_MORE_INFO);
    registerListener(DidomiEventType.NOTICE_CLICK_PRIVACY_POLICY);
    registerListener(DidomiEventType.NOTICE_CLICK_VIEW_VENDORS);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS);
    registerListener(DidomiEventType.PREFERENCES_CLICK_CATEGORY_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_CATEGORY_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL);
    registerListener(
      DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES
    );
    registerListener(DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS);
    registerListener(DidomiEventType.PREFERENCES_CLICK_PURPOSE_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_PURPOSE_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_RESET_ALL_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_SAVE_CHOICES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_SAVE_CHOICES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VIEW_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VIEW_VENDORS);
    registerListener(DidomiEventType.READY);
    registerListener(DidomiEventType.SHOW_NOTICE);
    registerListener(DidomiEventType.SYNC_DONE);

    /*Didomi.addEventListener(DidomiEventType.READY, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.READY, data });
      console.log("I'm ready");
    });

    Didomi.addEventListener(DidomiEventType.SHOW_NOTICE, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.SHOW_NOTICE, data });
      console.log('Show notice');
    });

    Didomi.addEventListener(DidomiEventType.CONSENT_CHANGED, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.CONSENT_CHANGED, data });
      console.log('Consent changed');
    });*/

    async function init() {
      await Didomi.initialize(
        '465ca0b2-b96f-43b4-a864-f87e18d2fd38',
        undefined,
        undefined,
        undefined,
        true,
        undefined,
        undefined
      );
      console.log('Finished init');

      Didomi.onReady().then(() => {
        setSdkStatus("READY");
      });
    }
    init();
  }, []);

  return (
    <SafeAreaView style={{ flex: 1 }}>
      <View style={styles.title}>
        <Text style={styles.title}>
          SDK STATUS: {sdkStatus}
        </Text>
        <Text style={styles.title}>
          LAST RECEIVED EVENT: {receivedEvent.name}
          {receivedEvent.data ? JSON.stringify(receivedEvent.data) : null}
        </Text>
      </View>
      <ScrollView>
        <View style={styles.container}>
          <Text style={styles.title}>METHODS</Text>
          <Methods />
          <Text style={styles.title}>GETTERS</Text>
          <Getters />
          <Text style={styles.title}>GETTERS PARAMS</Text>
          <GettersParams />
          <Text style={styles.title}>SETTERS</Text>
          <Setters />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 5,
    alignItems: 'center',
  },
});

export default App;
