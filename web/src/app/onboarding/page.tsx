'use client';

import {
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { useTranslations } from 'next-intl';
import dynamic from 'next/dynamic';
import type { ComponentType} from 'react';
import { useState } from 'react';

export interface OnboardingContentProps {
  onNextStep: () => void;
  onPreviousStep: () => void;
}

const OnboardingSteps: {
  tag: string;
  content: ComponentType<OnboardingContentProps>;
}[] = [
  {
    tag: 'create-profile',
    content: dynamic(() => import('./components/CreateProfile')),
  },
  {
    tag: 'finished',
    content: dynamic(() => import('./components/OnboardingFinished')),
  },
];

export default function Onboarding() {
  const t = useTranslations();

  const [step, setStep] = useState(0);
  const { tag, content: Content } = OnboardingSteps[step];

  return (
    <>
      <CardHeader>
        <span className='text-sm'>
          Step {step + 1} of {OnboardingSteps.length}
        </span>

        <CardTitle>{t(`onboarding.${tag}.title`)}</CardTitle>
        <CardDescription>{t(`onboarding.${tag}.description`)}</CardDescription>
      </CardHeader>

      <CardContent>
        <Content
          onNextStep={() => {
            setStep((step) => {
              return step === OnboardingSteps.length - 1 ? step : step + 1;
            });
          }}
          onPreviousStep={() => {
            setStep((step) => {
              return step === 0 ? step : step - 1;
            });
          }}
        />
      </CardContent>
    </>
  );
}
