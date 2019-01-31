Event Handler Demo: AEM 6.1
========

Task I

Automatic Versioning Task

Create OSGI service that subsribes for page content changes events under repository path /content/myapp. When such
content modification event occurs service should create new page version automatically using Java API available.
Content modification means any change with page content in repository, it could be change for page content itself or
change of any component content that is placed on that page.

The following requirements should be met:

    OSGI service should work only with content of pages (Sibling nodes of cq:Page node type);
    Service should take into account only pages located strictly under /content/myapp, not deeper;
    Pages with empty jcr:description property in jcr:content should be ignored. Note that jcr:description is usually
    empty for newly created pages so you will need to add it manually for pages for testing purposes;



Task II

Job For Properties Removal Listening

On each property removal under /content/myapp start a job which will:

    create a node with unique name under "/var/log/removedProperties"
    save propertyName and propertyPath under it
    No restrictions on page/property depth level





This a content package project generated using the multimodule-content-package-archetype.

Building
--------

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

Using with VLT
--------------

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

Specifying CRX Host/Port
------------------------

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>


