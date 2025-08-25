import SignInForm from './components/SignInForm';
import SocialLoginButtons from './components/SocialLoginButtons';
import {
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Separator } from '@/components/ui/separator';
import { getTranslations } from 'next-intl/server';
import Link from 'next/link';

export default async function SignIn() {
  const t = await getTranslations();

  return (
    <>
      <CardHeader>
        <CardTitle className='text-lg'>{t('signin.title')}</CardTitle>
        <CardDescription>{t('signin.description')}</CardDescription>
      </CardHeader>

      <CardContent>
        <SocialLoginButtons />

        <Separator className='my-4' />

        <SignInForm />

        <Separator className='my-4' />

        <div className='text-sm flex justify-center'>
          <Link href='/auth/signup'>
            <span className='underline hover:cursor-pointer text-foreground-link'>
              {t('signin.signup.2')}
            </span>
          </Link>
        </div>
      </CardContent>
    </>
  );
}
