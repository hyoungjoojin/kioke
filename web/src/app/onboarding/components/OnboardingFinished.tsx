import type { OnboardingContentProps } from '../page';
import { useUpdateProfileMutation } from '@/app/api/profiles/query';
import { Button } from '@/components/ui/button';
import { Routes } from '@/constant/routes';
import { useTranslations } from 'next-intl';
import { redirect } from 'next/navigation';

export default function OnboardingFinished({
  onPreviousStep,
}: OnboardingContentProps) {
  const t = useTranslations();

  const { mutate: updateProfile } = useUpdateProfileMutation();

  const previousButtonClickHandler = () => {
    onPreviousStep();
  };

  const continueButtonClickHandler = async () => {
    updateProfile(
      {
        onboarded: true,
      },
      {
        onSuccess: () => {
          redirect(Routes.HOME);
        },
      },
    );
  };

  return (
    <div className='flex gap-3'>
      <Button
        className='grow'
        variant='secondary'
        onClick={previousButtonClickHandler}
      >
        {t('onboarding.finished.previous')}
      </Button>

      <Button
        className='grow'
        variant='default'
        onClick={continueButtonClickHandler}
      >
        {t('onboarding.finished.continue')}
      </Button>
    </div>
  );
}
