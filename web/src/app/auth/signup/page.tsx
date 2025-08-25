import SignUpForm from './components/SignUpForm';
import {
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { useTranslations } from 'next-intl';

export default function SignUp() {
  const t = useTranslations();

  return (
    <>
      <CardHeader>
        <CardTitle className='text-lg'>{t('signup.title')}</CardTitle>
        <CardDescription>{t('signup.description')}</CardDescription>
      </CardHeader>

      <CardContent>
        <SignUpForm />
      </CardContent>
    </>
  );
}
