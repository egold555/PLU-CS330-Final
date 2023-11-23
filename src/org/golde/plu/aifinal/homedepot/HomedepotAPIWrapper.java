package org.golde.plu.aifinal.homedepot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomedepotAPIWrapper {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static Product lookup(String sku) throws IOException, ProductLookupException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"operationName\":\"productClientOnlyProduct\",\"variables\":{\"itemId\":\"" + sku + "\"},\"query\":\"query productClientOnlyProduct($storeId: String, $zipCode: String, $itemId: String!, $dataSource: String, $loyaltyMembershipInput: LoyaltyMembershipInput, $skipSpecificationGroup: Boolean = false, $skipSubscribeAndSave: Boolean = false, $skipKPF: Boolean = false, $skipInstallServices: Boolean = true) {\\n  product(itemId: $itemId, dataSource: $dataSource, loyaltyMembershipInput: $loyaltyMembershipInput) {\\n    fulfillment(storeId: $storeId, zipCode: $zipCode) {\\n      backordered\\n      fulfillmentOptions {\\n        type\\n        services {\\n          type\\n          locations {\\n            isAnchor\\n            inventory {\\n              isLimitedQuantity\\n              isOutOfStock\\n              isInStock\\n              quantity\\n              isUnavailable\\n              maxAllowedBopisQty\\n              minAllowedBopisQty\\n              __typename\\n            }\\n            type\\n            storeName\\n            storePhone\\n            locationId\\n            curbsidePickupFlag\\n            isBuyInStoreCheckNearBy\\n            distance\\n            state\\n            __typename\\n          }\\n          optimalFulfillment\\n          deliveryTimeline\\n          deliveryDates {\\n            startDate\\n            endDate\\n            __typename\\n          }\\n          deliveryCharge\\n          dynamicEta {\\n            hours\\n            minutes\\n            __typename\\n          }\\n          hasFreeShipping\\n          freeDeliveryThreshold\\n          totalCharge\\n          __typename\\n        }\\n        fulfillable\\n        __typename\\n      }\\n      anchorStoreStatus\\n      anchorStoreStatusType\\n      backorderedShipDate\\n      bossExcludedShipStates\\n      sthExcludedShipState\\n      bossExcludedShipState\\n      excludedShipStates\\n      seasonStatusEligible\\n      onlineStoreStatus\\n      onlineStoreStatusType\\n      inStoreAssemblyEligible\\n      __typename\\n    }\\n    info {\\n      dotComColorEligible\\n      hidePrice\\n      ecoRebate\\n      quantityLimit\\n      sskMin\\n      sskMax\\n      unitOfMeasureCoverage\\n      wasMaxPriceRange\\n      wasMinPriceRange\\n      fiscalYear\\n      productDepartment\\n      classNumber\\n      forProfessionalUseOnly\\n      globalCustomConfigurator {\\n        customButtonText\\n        customDescription\\n        customExperience\\n        customExperienceUrl\\n        customTitle\\n        __typename\\n      }\\n      paintBrand\\n      movingCalculatorEligible\\n      label\\n      prop65Warning\\n      returnable\\n      recommendationFlags {\\n        visualNavigation\\n        reqItems\\n        batItems\\n        packages\\n        __typename\\n      }\\n      replacementOMSID\\n      hasSubscription\\n      minimumOrderQuantity\\n      projectCalculatorEligible\\n      subClassNumber\\n      calculatorType\\n      isLiveGoodsProduct\\n      protectionPlanSku\\n      hasServiceAddOns\\n      consultationType\\n      isBuryProduct\\n      isSponsored\\n      isGenericProduct\\n      sponsoredBeacon {\\n        onClickBeacon\\n        onViewBeacon\\n        __typename\\n      }\\n      sponsoredMetadata {\\n        campaignId\\n        placementId\\n        slotId\\n        __typename\\n      }\\n      productSubType {\\n        name\\n        link\\n        __typename\\n      }\\n      categoryHierarchy\\n      samplesAvailable\\n      customerSignal {\\n        previouslyPurchased\\n        __typename\\n      }\\n      productDepartmentId\\n      augmentedReality\\n      swatches {\\n        isSelected\\n        itemId\\n        label\\n        swatchImgUrl\\n        url\\n        value\\n        __typename\\n      }\\n      totalNumberOfOptions\\n      __typename\\n    }\\n    itemId\\n    dataSources\\n    identifiers {\\n      canonicalUrl\\n      brandName\\n      itemId\\n      modelNumber\\n      productLabel\\n      storeSkuNumber\\n      upcGtin13\\n      specialOrderSku\\n      toolRentalSkuNumber\\n      rentalCategory\\n      rentalSubCategory\\n      upc\\n      productType\\n      isSuperSku\\n      parentId\\n      roomVOEnabled\\n      sampleId\\n      __typename\\n    }\\n    availabilityType {\\n      discontinued\\n      status\\n      type\\n      buyable\\n      __typename\\n    }\\n    details {\\n      description\\n      collection {\\n        url\\n        collectionId\\n        name\\n        __typename\\n      }\\n      highlights\\n      descriptiveAttributes {\\n        name\\n        value\\n        bulleted\\n        sequence\\n        __typename\\n      }\\n      additionalResources {\\n        infoAndGuides {\\n          name\\n          url\\n          __typename\\n        }\\n        installationAndRentals {\\n          contentType\\n          name\\n          url\\n          __typename\\n        }\\n        diyProjects {\\n          contentType\\n          name\\n          url\\n          __typename\\n        }\\n        __typename\\n      }\\n      installation {\\n        leadGenUrl\\n        __typename\\n      }\\n      __typename\\n    }\\n    media {\\n      images {\\n        url\\n        type\\n        subType\\n        sizes\\n        __typename\\n      }\\n      video {\\n        shortDescription\\n        thumbnail\\n        url\\n        videoStill\\n        link {\\n          text\\n          url\\n          __typename\\n        }\\n        title\\n        type\\n        videoId\\n        longDescription\\n        __typename\\n      }\\n      threeSixty {\\n        id\\n        url\\n        __typename\\n      }\\n      augmentedRealityLink {\\n        usdz\\n        image\\n        __typename\\n      }\\n      richContent {\\n        content\\n        displayMode\\n        richContentSource\\n        __typename\\n      }\\n      __typename\\n    }\\n    pricing(storeId: $storeId) {\\n      promotion {\\n        dates {\\n          end\\n          start\\n          __typename\\n        }\\n        type\\n        description {\\n          shortDesc\\n          longDesc\\n          __typename\\n        }\\n        dollarOff\\n        percentageOff\\n        savingsCenter\\n        savingsCenterPromos\\n        specialBuySavings\\n        specialBuyDollarOff\\n        specialBuyPercentageOff\\n        experienceTag\\n        subExperienceTag\\n        anchorItemList\\n        itemList\\n        reward {\\n          tiers {\\n            minPurchaseAmount\\n            minPurchaseQuantity\\n            rewardPercent\\n            rewardAmountPerOrder\\n            rewardAmountPerItem\\n            rewardFixedPrice\\n            __typename\\n          }\\n          __typename\\n        }\\n        nvalues\\n        brandRefinementId\\n        __typename\\n      }\\n      value\\n      alternatePriceDisplay\\n      alternate {\\n        bulk {\\n          pricePerUnit\\n          thresholdQuantity\\n          value\\n          __typename\\n        }\\n        unit {\\n          caseUnitOfMeasure\\n          unitsOriginalPrice\\n          unitsPerCase\\n          value\\n          __typename\\n        }\\n        __typename\\n      }\\n      original\\n      mapAboveOriginalPrice\\n      message\\n      preferredPriceFlag\\n      specialBuy\\n      unitOfMeasure\\n      __typename\\n    }\\n    reviews {\\n      ratingsReviews {\\n        averageRating\\n        totalReviews\\n        __typename\\n      }\\n      __typename\\n    }\\n    seo {\\n      seoKeywords\\n      seoDescription\\n      __typename\\n    }\\n    specificationGroup @skip(if: $skipSpecificationGroup) {\\n      specifications {\\n        specName\\n        specValue\\n        __typename\\n      }\\n      specTitle\\n      __typename\\n    }\\n    taxonomy {\\n      breadCrumbs {\\n        label\\n        url\\n        browseUrl\\n        creativeIconUrl\\n        deselectUrl\\n        dimensionName\\n        refinementKey\\n        __typename\\n      }\\n      brandLinkUrl\\n      __typename\\n    }\\n    favoriteDetail {\\n      count\\n      __typename\\n    }\\n    sizeAndFitDetail {\\n      attributeGroups {\\n        attributes {\\n          attributeName\\n          dimensions\\n          __typename\\n        }\\n        dimensionLabel\\n        productType\\n        __typename\\n      }\\n      __typename\\n    }\\n    subscription @skip(if: $skipSubscribeAndSave) {\\n      defaultfrequency\\n      discountPercentage\\n      subscriptionEnabled\\n      __typename\\n    }\\n    badges(storeId: $storeId) {\\n      label\\n      color\\n      creativeImageUrl\\n      endDate\\n      message\\n      name\\n      timerDuration\\n      timer {\\n        timeBombThreshold\\n        daysLeftThreshold\\n        dateDisplayThreshold\\n        message\\n        __typename\\n      }\\n      __typename\\n    }\\n    keyProductFeatures @skip(if: $skipKPF) {\\n      keyProductFeaturesItems {\\n        features {\\n          name\\n          refinementId\\n          refinementUrl\\n          value\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    seoDescription\\n    installServices(storeId: $storeId, zipCode: $zipCode) @skip(if: $skipInstallServices) {\\n      scheduleAMeasure\\n      gccCarpetDesignAndOrderEligible\\n      __typename\\n    }\\n    dataSource\\n    __typename\\n  }\\n}\\n\"}");
        Request request = new Request.Builder()
                .url("https://www.homedepot.com/federation-gateway/graphql?opname=productClientOnlyProduct")
                .method("POST", body)
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("content-type", "application/json")
                .addHeader("x-experience-name", "general-merchandise")
                .build();

        Response response = client.newCall(request).execute();

        if(response.code() != 200) {
            throw new ProductLookupException("Failed to lookup product! Code: " + response.code() + " Message: " + response.message());
        }

        String responseBody = response.body().string();
        //System.out.println(responseBody);

        JsonObject jsonObject = GSON.fromJson(responseBody, JsonObject.class);
        JsonObject data = jsonObject.getAsJsonObject("data").getAsJsonObject("product");

        return parseFromJson(data);
    }

    private static Product parseFromJson(JsonObject data) {

        final JsonObject details = data.getAsJsonObject("details");
        final JsonObject identifiers = data.getAsJsonObject("identifiers");
        final JsonArray mediaImages = data.get("media").getAsJsonObject().get("images").getAsJsonArray();

        final String name = identifiers.get("productLabel").getAsString();
        final String description = details.get("description").getAsString();

        String imageURL = null;
        for(JsonElement ele : mediaImages) {
            JsonObject image = ele.getAsJsonObject();

            if(image.get("type") != null && !image.get("type").isJsonNull() && image.get("subType") != null && !image.get("subType").isJsonNull()) {
                if(image.get("type").getAsString().equalsIgnoreCase("IMAGE") && image.get("subType").getAsString().equalsIgnoreCase("PRIMARY")) {
                    imageURL = image.get("url").getAsString();
                    JsonArray sizes = image.get("sizes").getAsJsonArray();
                    String theSize = sizes.get(sizes.size() - 1).getAsString();
                    imageURL = imageURL.replace("<SIZE>", theSize);
                }
            }


        }


        return new Product(name, description, imageURL);
    }

}
