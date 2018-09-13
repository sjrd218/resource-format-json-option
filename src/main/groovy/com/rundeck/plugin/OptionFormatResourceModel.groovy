package com.rundeck.plugin

import com.dtolabs.rundeck.core.common.INodeEntry
import com.dtolabs.rundeck.core.common.INodeSet
import com.dtolabs.rundeck.core.plugins.Plugin
import com.dtolabs.rundeck.core.plugins.configuration.Description
import com.dtolabs.rundeck.core.resources.format.ResourceFormatGenerator
import com.dtolabs.rundeck.core.resources.format.ResourceFormatGeneratorException
import com.dtolabs.rundeck.plugins.ServiceNameConstants
import com.dtolabs.rundeck.plugins.util.DescriptionBuilder
import com.fasterxml.jackson.databind.ObjectMapper

@Plugin(name = OptionFormatResourceModel.SERVICE_PROVIDER_TYPE, service = ServiceNameConstants.ResourceFormatGenerator)
class OptionFormatResourceModel implements ResourceFormatGenerator{
    public static final String SERVICE_PROVIDER_TYPE = "option-json";

    private static final Description DESCRIPTION = DescriptionBuilder.builder()
                                                                     .name(SERVICE_PROVIDER_TYPE)
                                                                     .title("Option Resource JSON")
                                                                     .description(
                                                                     "The Rundeck Resource Option JSON format " +
                                                                     "(bundled)")
                                                                     .build()

    private final ObjectMapper test = new ObjectMapper();

    Description getDescription() {
        return DESCRIPTION;
    }

    @Override
    Set<String> getFileExtensions() {
        return Collections.unmodifiableSet(
                new HashSet<>(
                        Collections.singletonList(
                                "json"
                        )
                )
        )
    }

    @Override
    List<String> getMIMETypes() {
        return Collections.unmodifiableList(
                Arrays.asList(
                        "application/json", "text/json"
                )
        )
    }

    @Override
    void generateDocument(final INodeSet nodeset, final OutputStream stream)
            throws ResourceFormatGeneratorException, IOException {
        try {
            test.writeValue(stream, convertNodes(nodeset));
        } catch (IOException e) {
            throw new ResourceFormatGeneratorException(e);
        }
    }

    private Object convertNodes(final INodeSet nodeset) {
        List<Map<String, String>> stringMapHashMap = new ArrayList<>();
        for (INodeEntry node : nodeset) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", node.nodename)
            map.put("value", node.nodename)
            stringMapHashMap.add(map)
        }
        return stringMapHashMap;
    }
}
