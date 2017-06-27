package edu.fmi.genderclassify.weka.train;

import edu.fmi.genderclassify.dataimport.Fields;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.utils.Conversion;
import javafx.util.Pair;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Miroslav Kramolinski
 *
 * Supplies feature vectors that are already filled with data and ready for action
 */
public class FeatureVectorsFactory {
    public static Instances getStandardFeaturesInstances(List<Pair<String, Attribute>> attributesList, List<Observation> observations) {
        Instances trainingSet = new Instances(
                "Standard set",
                (ArrayList<Attribute>) attributesList
                        .stream()
                        .map(pair -> pair.getValue())
                        .collect(Collectors.toList()),
                observations.size());

        for (Observation observation : observations) {
            Instance instance = new DenseInstance(attributesList.size());

            for (Pair<String, Attribute> attributePair : attributesList) {
                if (attributePair.getKey().equalsIgnoreCase(Fields.USER_ID.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getId()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getGolden()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.UNIT_STATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getObservationState()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.TRUSTED_JUDGEMENTS.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getTrustedJudgements()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.LAST_JUDGEMENT_TIME.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getLastJudgement()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.GENDER.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getGender()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.GENDER_CONFIDENCE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getGenderConfidence()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_EXISTS.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getProfileExists()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_EXISTS_CONFIDENCE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getProfile().getProfileExistsConfidence()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_CREATION_DATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getCreated()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_DESCRIPTION.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getDescription());

                if(attributePair.getKey().equalsIgnoreCase(Fields.FAVORITES_NUMBER.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getNumberOfFavorites()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.GENDER_GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getGenderGold()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.LINK_COLOR.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getLinkColor());

                if(attributePair.getKey().equalsIgnoreCase(Fields.USERNAME.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getUsername());

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_YN_VALUE_GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getProfileYnGolden()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_IMAGE_LINK.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getProfileImageLink());

                if(attributePair.getKey().equalsIgnoreCase(Fields.RETWEET_COUNT.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getNumberOfRetweets()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.SIDEBAR_COLOR.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getSidebarColor());

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_TEXT.name()))
                    instance.setValue(attributePair.getValue(), observation.getTweet().getText());

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_COORDINATES.name() + "_LATITUDE")) {
                    if(observation.getTweet().getCoordinates() != null)
                        instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getTweet().getCoordinates().getKey()));
                    else
                        instance.setValue(attributePair.getValue(), 0);
                }

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_COORDINATES.name() + "_LONGITUDE")) {
                    if(observation.getTweet().getCoordinates() != null)
                        instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getTweet().getCoordinates().getValue()));
                    else
                        instance.setValue(attributePair.getValue(), 0);
                }

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEETS_COUNT.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getTweetCount()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_CREATION_DATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getTweet().getCreationDate()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_ID.name()))
                    instance.setValue(attributePair.getValue(), observation.getTweet().getId());

                if(attributePair.getKey().equalsIgnoreCase(Fields.TWEET_LOCATION.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getTweet().getLocation()));

                if(attributePair.getKey().equalsIgnoreCase(Fields.USER_TIMEZONE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getTimezone()));
            }

            trainingSet.add(instance);
        }

        trainingSet.setClassIndex(attributesList.size() - 1);

        return trainingSet;
    }

    public static Instances getStandardNonStringInstances(List<Pair<String, Attribute>> attributesList, List<Observation> observations) {
        List<Pair<String, Attribute>> attributes = attributesList
                .stream()
                .filter(pair -> !pair.getValue().isString())
                .collect(Collectors.toList());

        Instances trainingSet = new Instances(
                "Standard set",
                (ArrayList<Attribute>) attributes
                        .stream()
                        .map(pair -> pair.getValue())
                        .collect(Collectors.toList()),
                observations.size());

        for (Observation observation : observations) {
            Instance instance = new DenseInstance(attributes.size());

            for (Pair<String, Attribute> attributePair : attributes) {
                if (attributePair.getValue().isString())
                    continue;

                if (attributePair.getKey().equalsIgnoreCase(Fields.USER_ID.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getId()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getGolden()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.UNIT_STATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getObservationState()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.TRUSTED_JUDGEMENTS.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getTrustedJudgements()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.LAST_JUDGEMENT_TIME.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getLastJudgement()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.GENDER.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getGender()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.GENDER_CONFIDENCE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getGenderConfidence()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_EXISTS.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getProfileExists()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_EXISTS_CONFIDENCE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getProfile().getProfileExistsConfidence()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_CREATION_DATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getCreated()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_DESCRIPTION.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getDescription());

                if (attributePair.getKey().equalsIgnoreCase(Fields.FAVORITES_NUMBER.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getNumberOfFavorites()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.GENDER_GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getGenderGold()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.LINK_COLOR.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getLinkColor());

                if (attributePair.getKey().equalsIgnoreCase(Fields.USERNAME.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getUsername());

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_YN_VALUE_GOLDEN.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getProfile().getProfileYnGolden()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.PROFILE_IMAGE_LINK.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getProfileImageLink());

                if (attributePair.getKey().equalsIgnoreCase(Fields.RETWEET_COUNT.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getNumberOfRetweets()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.SIDEBAR_COLOR.name()))
                    instance.setValue(attributePair.getValue(), observation.getUser().getProfile().getSidebarColor());

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_TEXT.name()))
                    instance.setValue(attributePair.getValue(), observation.getTweet().getText());

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_COORDINATES.name() + "_LATITUDE")) {
                    if (observation.getTweet().getCoordinates() != null)
                        instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getTweet().getCoordinates().getKey()));
                    else
                        instance.setValue(attributePair.getValue(), 0);
                }

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_COORDINATES.name() + "_LONGITUDE")) {
                    if (observation.getTweet().getCoordinates() != null)
                        instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getTweet().getCoordinates().getValue()));
                    else
                        instance.setValue(attributePair.getValue(), 0);
                }

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEETS_COUNT.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getDoubleValue(observation.getUser().getTweetCount()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_CREATION_DATE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getTweet().getCreationDate()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_ID.name()))
                    instance.setValue(attributePair.getValue(), observation.getTweet().getId());

                if (attributePair.getKey().equalsIgnoreCase(Fields.TWEET_LOCATION.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getTweet().getLocation()));

                if (attributePair.getKey().equalsIgnoreCase(Fields.USER_TIMEZONE.name()))
                    instance.setValue(attributePair.getValue(), Conversion.getValueAsStr(observation.getUser().getTimezone()));
            }

            trainingSet.add(instance);
        }

        trainingSet.setClassIndex(attributes.size() - 1);

        return trainingSet;
    }
}