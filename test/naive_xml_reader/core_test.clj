(ns naive-xml-reader.core-test
  (:require [expectations :refer :all]
            [naive-xml-reader.core :refer [read-xml]]))

(expect nil (read-xml "<d></d>"))

(expect "leaf" (read-xml "<d>leaf</d>"))

(expect {:a nil} (read-xml "<d><a></a></d>"))

(expect {:a nil
         :b nil}
        (read-xml "<d><a></a><b></b></d>"))

(expect {:a {:b nil}}
        (read-xml "<d><a><b></b></a></d>"))

(expect {:a {:b "leaf"}}
        (read-xml "<d><a><b>leaf</b></a></d>"))

(expect {:camel-case nil} (read-xml "<d><camelCase></camelCase></d>"))

(expect {:a ["1" "2"]} (read-xml "<d><a>1</a><a>2</a></d>"
                                 {:list-paths [[:a]]}))

(expect {:l {:a ["1" "2"]}} (read-xml "<d><l><a>1</a><a>2</a></l></d>"
                                      {:list-paths [[:l :a]]}))

(expect {:l [{:a ["1" "2"]}
             {:a ["3" "4"]}]}
        (read-xml "<d><l><a>1</a><a>2</a></l><l><a>3</a><a>4</a></l></d>"
                  {:list-paths [[:l]
                                [:l :a]]}))

(expect
 {:message {:sid "ghijkl"
            :date-created "Mon, 22 Feb 2016 12:35:28 +0000"
            :date-updated "Mon, 22 Feb 2016 12:35:28 +0000"
            :date-sent nil
            :account-sid "abcdef"
            :to "+4700000797"
            :from "+15005550006"
            :messaging-service-sid nil
            :body "Yoyo"
            :status "queued"
            :num-segments "1"
            :num-media "0"
            :direction "outbound-api"
            :api-version "2010-04-01"
            :price nil
            :price-unit "USD"
            :uri "/2010-04-01/Accounts/abcdef/Messages/ghijkl"
            :subresource-uris {:media ["/2010-04-01/Accounts/abcdef/Messages/ghijkl/Media"]}}}
 (read-xml
  "<?xml version='1.0' encoding='UTF-8'?>
  <TwilioResponse>
    <Message>
      <Sid>ghijkl</Sid>
      <DateCreated>Mon, 22 Feb 2016 12:35:28 +0000</DateCreated>
      <DateUpdated>Mon, 22 Feb 2016 12:35:28 +0000</DateUpdated>
      <DateSent/>
      <AccountSid>abcdef</AccountSid>
      <To>+4700000797</To>
      <From>+15005550006</From>
      <MessagingServiceSid/>
      <Body>Yoyo</Body>
      <Status>queued</Status>
      <NumSegments>1</NumSegments>
      <NumMedia>0</NumMedia>
      <Direction>outbound-api</Direction>
      <ApiVersion>2010-04-01</ApiVersion>
      <Price/>
      <PriceUnit>USD</PriceUnit>
      <Uri>/2010-04-01/Accounts/abcdef/Messages/ghijkl</Uri>
      <SubresourceUris>
        <Media>/2010-04-01/Accounts/abcdef/Messages/ghijkl/Media</Media>
      </SubresourceUris>
    </Message>
  </TwilioResponse>"
  {:list-paths [[:message :subresource-uris :media]]}))

(expect
 {:client "demo"
  :pw "password"
  :msglst {:msg [{:text "Test message1", :rcv "4793000000"}
                 {:text "Test message2", :rcv "4793000000"}]}}
 (read-xml
  "<SESSION>
     <CLIENT>demo</CLIENT>
     <PW>password</PW>
     <MSGLST>
       <MSG>
         <TEXT>Test message1</TEXT>
         <RCV>4793000000</RCV>
       </MSG>
       <MSG>
         <TEXT>Test message2</TEXT>
         <RCV>4793000000</RCV>
       </MSG>
     </MSGLST>
   </SESSION>"
  {:list-paths [[:msglst :msg]]}))
