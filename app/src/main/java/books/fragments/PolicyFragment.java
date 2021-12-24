package books.fragments;

import static books.activity.HomeBookActivity.imgLogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.activity.HomeBookActivity;
import books.adapter.PolicyListAdapter;
import books.models.PolicyList;

public class PolicyFragment extends BaseFragment {

    public static int REQUEST_CODE = 102;


    public PolicyFragment() {
        // Required empty public constructor
    }


    public static PolicyFragment newInstance() {
        PolicyFragment fragment = new PolicyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_policy, container, false);
        imgLogo.setVisibility(View.GONE);
        HomeBookActivity.bottomNavigationView.setVisibility(View.VISIBLE);

        RecyclerView rvPolicyList = view.findViewById(R.id.rv_policy);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvPolicyList.setLayoutManager(layoutManager);
        rvPolicyList.setAdapter(new PolicyListAdapter(context, getPolicyData()));

        return view;
    }

    private ArrayList<PolicyList> getPolicyData() {
        PolicyList policyList = new PolicyList();
        policyList.setPolicyCategory("Personal Information One Door Services");
        policyList.setPolicyDesc("(“ODS”) is the licensed owner of the brand throtelservices.com. As a Customer you are advised to please read the Privacy Policy carefully. Byaccessing the services provided by the Site you agree to the collection and use of your data by ODS in the manner provided in this Privacy Policy.");

        PolicyList policyList1 = new PolicyList();
        policyList1.setPolicyCategory("Services overview");
        policyList1.setPolicyDesc("As part of the One Door Grocery Customer registration process on the Site- throtelservices.com, may collect the following personally identifiable information about you: Name including first and last name, alternate email address, mobile phone number and contact details, Postal code, \n" +
                "Demographic profile (like your age, gender, occupation, education, address etc.) and information about the pages on the site you visit/access, the links you click on the site, the number of times you access the page and any such browsing information.");

        PolicyList policyList2 = new PolicyList();
        policyList2.setPolicyCategory("Eligibility");
        policyList2.setPolicyDesc("Services of the Site would be available to only select geographies in India. Persons who are \"incompetent to contract\" within the meaning of the Indian Contract Act, 1872 including un-discharged insolvents etc. are not eligible to use the Site. If you are a minor i.e. under the age of 18 years but at least 13 years of age you may use the Site only under the supervision of a parent or legal guardian who agrees to be bound by these Terms of Use. If your age is below 18 years your parents or legal guardians can transact on behalf of you if they are registered users. You are prohibited from purchasing any material which is for adult consumption and the sale of which to minors is prohibited");

        PolicyList policyList3 = new PolicyList();
        policyList3.setPolicyCategory("License & Site access");
        policyList3.setPolicyDesc("throtelservices.com grants you a limited sub-license to access and make personal use of this site and not to download (other than page caching) or modify it, or any portion of it, except with express written consent of ODS. This license does not include any resale or commercial use of this site or its contents; any collection and use of any product listings, descriptions, or prices; any derivative use of this site or its contents; any downloading or copying of account information for the benefit of anothermerchant; or any use of data mining, robots, or similar data gathering and extraction tools. This site or any portion of this site may not be reproduced, duplicated, copied, sold, resold, visited, or otherwise exploited for any commercial purpose without express written consent of ODS. You may not frame or utilize framing techniques to enclose any trademark, logo, or other proprietary information (including images, text, page layout, or form) of the Site or of ODS and its affiliates without express written consent. You may not use any meta tags or any other \"hidden text\" utilizing the Site’s or ODS’s name or trademarks without the express written consent of ODS. Any unauthorized use terminates the permission or license granted by ODS.");

        PolicyList policyList4 = new PolicyList();
        policyList4.setPolicyCategory("Account & Registration Obligations");
        policyList4.setPolicyDesc("All shoppers have to register and login for placing orders on the Site. You have to keep your account and registration details current and correct for communications related to your purchases from the site. By agreeing to the terms and conditions, the shopper agrees to receive promotional communication and newsletters upon registration. The customer can opt out either by unsubscribing in \"My Account\" or by contacting the customer service.");

        PolicyList policyList5 = new PolicyList();
        policyList5.setPolicyCategory("Pricing");
        policyList5.setPolicyDesc("All the products listed on the Site will be sold at MRP unless otherwise specified. The prices mentioned at the time of ordering will be the prices charged on the date of the delivery. Although prices of most of the products do not fluctuate on a daily basis but some of the commodities and freshfood prices do change on a daily basis. In case the prices are higher or lower on the date of delivery not additional charges will be collected or refunded as the case may be at the time of the delivery of the order.");

        PolicyList policyList6 = new PolicyList();
        policyList6.setPolicyCategory("Cancellation by Site / Customer");
        policyList6.setPolicyDesc("You as a customer can cancel your order anytime up to the cut-off time of the slot for which you have placed an order by calling our customer service. In such a case we will refund any payments already made by you for the order. If we suspect any fraudulent transaction by any customer or any transaction which defies the terms & conditions of using the website, we at our sole discretion could cancel such orders. We will maintain a negative list of all fraudulent transactions and customers and would deny access to them or cancel any orders placed by them.");

        PolicyList policyList7 = new PolicyList();
        policyList7.setPolicyCategory("Return & Refunds");
        policyList7.setPolicyDesc("We have a \"no questions asked return and refund policy\" which entitles all our members to return theproduct at the time of delivery if due to some reason they are not satisfied with the quality or freshness of the product. We will take the returned product back with us and issue a credit note for the value of the return products which will be credited to your account on the Site. This can be used to pay your subsequent shopping bills.");

        PolicyList policyList8 = new PolicyList();
        policyList8.setPolicyCategory("You Agree and Confirm");
        policyList8.setPolicyDesc("1.That in the event that a non-delivery occurs on account of a mistake by you (i.e. wrong name or address or any other wrong information) any extra cost incurred by ODS for redelivery shall be claimed from you.2.That you will use the services provided by the Site, its affiliates, consultants and contracted companies, for lawful purposes only and comply with all applicable laws and regulations while usingand transacting on the Site.3.You will provide authentic and true information in all instances where such information is requested of you. ODS reserves the right to confirm and validate the information and other details provided by you at any point of time. If upon confirmation your details are found not to be true (wholly or partly), it has the right in its sole discretion to reject the registration and debar you from using the Services and / or other affiliated websites without prior intimation whatsoever.4.You authorise throtel to contact you for any transactional purposes related to your order/account.5.That you are accessing the services available on this Site and transacting at your sole risk and are using your best and prudent judgment before entering into any transaction through this Site.6.That the address at which delivery of the product ordered by you is to be made will be correct and proper in all respects.7.That before placing an order you will check the product description carefully. By placing an order for a product you agree to be bound by the conditions of sale included in the item's description.");

        PolicyList policyList9 = new PolicyList();
        policyList9.setPolicyCategory("You may not use the Site for any of the following purposes:");
        policyList9.setPolicyDesc("1.Disseminating any unlawful, harassing, libellous, abusive, threatening, harmful, vulgar, obscene, or otherwise objectionable material.2.Transmitting material that encourages conduct that constitutes a criminal offence or results in civil liability or otherwise breaches any relevant laws, regulations or code of practice.3.Gaining unauthorized access to other computer systems.4.Interfering with any other person's use or enjoyment of the Site.5.Breaching any applicable laws;6.Interfering or disrupting networks or web sites connected to the Site.");

        ArrayList<PolicyList> lists = new ArrayList<>();
        lists.add(policyList);
        lists.add(policyList1);
        lists.add(policyList2);
        lists.add(policyList3);
        lists.add(policyList4);
        lists.add(policyList5);
        lists.add(policyList6);
        lists.add(policyList7);
        lists.add(policyList8);
        lists.add(policyList9);

        return lists;

    }


}
