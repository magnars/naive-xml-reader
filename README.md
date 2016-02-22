# naive-xml-reader

A naive Clojure library that turns XML into maps. Of course, XML documents can't
be properly represented by maps - they are trees. That's the naive part.

## Install

Add `[naive-xml-reader "0.1.0"]` to `:dependencies` in your `project.clj`.

## Usage

Start by refering `read-xml`:

```clojure
(require '[naive-xml-reader.core :refer [read-xml]])
```

With some very basic XML, things are simple:

```xml
<Document>
  <Title>Hello</Title>
  <Artist>Adele Adkins</Artist>
</Document>
```

```clojure
(read-xml (slurp "that example above"))
;; => {:title "Hello", :artist "Adele Adkins"}
```

#### Enter lists of tags

Given repeating tags, we'll create lists. But we need to know ahead of time.
Otherwise there's no way of knowing the difference between a list with one
element and a map.

```xml
<Document>
  <Title>Hello</Title>
  <Artist>Adele Adkins</Artist>
  <Artist>Greg Kurstin</Artist>
</Document>
```

```clojure
(read-xml (slurp "that example above")
          {:list-paths [[:artist]]})
;; => {:title "Hello", :artist ["Adele Adkins", "Greg Kurstin"]}
```

For more examples, see the tests.

#### But what about attributes?

They are ignored. Outrageous? Sure. But this naive approach can only take us so
far. You might want to look into using `clojure.xml` and such.

## Should I use naive-xml-reader?

Probably not. Its naive approach only works for simple schema. It also reads the
entire XML structure into memory. Then again, you probably shouldn't be using
XML either.

I'd say only use it for small simple XML documents where pulling out the big
guns is just too much work.

## License

Copyright © 2016 Magnar Sveen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
