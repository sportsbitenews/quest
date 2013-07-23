package shef.mt.pipelines;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import shef.mt.features.util.FeatureManager;
import shef.mt.tools.*;
/**
 import shef.mt.tools.MorphAnalysisProcessor;
 import shef.mt.tools.NGramProcessor;
 import shef.mt.tools.POSProcessor;
 import shef.mt.tools.PPLProcessor;
 import shef.mt.tools.ResourceProcessor;
 import shef.mt.tools.TopicDistributionProcessor;
 import shef.mt.tools.TriggersProcessor;
 */
import shef.mt.util.PropertiesManager;

public class DynamicPipeline extends ResourcePipeline {

	ArrayList<ResourceProcessor> resources = new ArrayList<ResourceProcessor>();

	public DynamicPipeline(PropertiesManager propertiesManager, FeatureManager featureManager) {

		Set<String> requiredResourceNames = featureManager.getFeatureResources();
		System.out.println(requiredResourceNames.toString());

		
		//running existing processors automatically
		ArrayList<ResourceProcessor> resourceProcessors = new ArrayList<ResourceProcessor>();
		Reflections reflections = new Reflections("shef.mt.tools");
		Set<Class<? extends ResourceProcessor>> subTypes = reflections
				.getSubTypesOf(ResourceProcessor.class);

		for (Class<? extends ResourceProcessor> subType : subTypes) {
			try {
				resourceProcessors.add(subType.newInstance());
			}
			catch (Exception e) {
	            e.printStackTrace();
	        }
		}

		for (ResourceProcessor resourceProcessor : resourceProcessors) {
			//String resourceName = resourceProcessor.getName();
			//System.out.println(resourceProcessor.getName());
			//if (requiredResourceNames.contains(resourceName)) {
			resources.add(resourceProcessor);
			//}
		}
		initialize_resources(resources, propertiesManager, featureManager);

		/**
		 * ResourceProcessor bParser = new BParserProcessor(); ResourceProcessor
		 * topicDistribution = new TopicDistributionProcessor();
		 * ResourceProcessor morphAnalysis = new MorphAnalysisProcessor();
		 * ResourceProcessor triggers = new TriggersProcessor();
		 * ResourceProcessor ngramProcessor = new NGramProcessor();
		 * ResourceProcessor pplProcessor = new PPLProcessor();
		 * ResourceProcessor posProcessor = new POSProcessor();
		 * 
		 * resourceProcessors.add(bParser);
		 * resourceProcessors.add(topicDistribution);
		 * resourceProcessors.add(morphAnalysis);
		 * resourceProcessors.add(triggers);
		 * resourceProcessors.add(ngramProcessor);
		 * resourceProcessors.add(pplProcessor);
		 * resourceProcessors.add(posProcessor);
		 * //resourceProcessors.add(mtOutputProcessor);
		 * //resourceProcessors.add(nerProcessor);
		 */

	}
}