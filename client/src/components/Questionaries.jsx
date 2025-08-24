import React from "react";

const faqs = [
  {
    question: "What is the Disaster Relief System?",
    answer:
      `It is a platform designed to connect needy people with resources, and affected communities during disasters for fast and efficient relief 
      through the use of AI.`,
  },
  {
    question: "Is this a legit platform and is my information safe?",
    answer:
      `Yes, we are an actual platform to help connect people with resources in the most efficient way possible by utilizing complex algorithms and AI. 
      We prioritize your privacy you data is only shown to people you need to connect with to provide you aid.`,
  },
  {
    question: "Who can request help?",
    answer:
      `Anyone affected by a disaster can request help through our platform, using the 'Request Relief' button at the top right corner, after filling a small 
      necessary form, your mobile will be verified and the aid will be on its way.`,
  },
];

const Questionaries = () => {
  return (
    <div className="py-10 px-4 max-w-2xl mx-auto">
      <h2 className="text-xl font-semibold mb-5 text-gray-700">
        Frequently Asked Questions
      </h2>
      <div className="space-y-3">
        {faqs.map((faq, idx) => (
          <div key={idx} className="bg-white rounded-lg shadow p-5">
            <h3 className="font-medium text-gray-800 mb-2">{faq.question}</h3>
            <p className="text-gray-600 text-sm">{faq.answer}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Questionaries;
