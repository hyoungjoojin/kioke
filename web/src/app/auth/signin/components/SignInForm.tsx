'use client';

import { signIn } from '@/app/api/auth';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { ErrorCode } from '@/constant/error';
import logger from '@/lib/logger';
import { zodResolver } from '@hookform/resolvers/zod';
import { useTranslations } from 'next-intl';
import { redirect } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import z from 'zod';

const SignInFormSchema = z.object({
  email: z.email({
    message: 'signin.form.error.invalid-email',
  }),
  password: z.string().nonempty({
    message: 'signin.form.error.empty-field',
  }),
});
type SignInFormSchemaType = z.infer<typeof SignInFormSchema>;

export default function SignInForm() {
  const t = useTranslations();

  const signInForm = useForm<SignInFormSchemaType>({
    resolver: zodResolver(SignInFormSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  });

  const signInFormSubmitHandler = async (credentials: SignInFormSchemaType) =>
    await signIn(credentials).then((result) =>
      result
        .map((_) => {
          redirect('/');
        })
        .mapErr((error) => {
          logger.debug(error);

          switch (error.code) {
            case ErrorCode.BAD_CREDENTIALS:
              toast.error(t('error.bad-credentials'));
              break;

            case ErrorCode.NETWORK_REQUEST_FAILED:
              toast.error(t('error.network-failed'));
              break;
          }
        }),
    );

  return (
    <Form {...signInForm}>
      <form onSubmit={signInForm.handleSubmit(signInFormSubmitHandler)}>
        <div className='mt-3 mb-5'>
          <FormField
            name='email'
            control={signInForm.control}
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>
                    {t('signin.form.fields.email')}
                    <FormMessage t={t} />
                  </FormLabel>
                  <FormControl>
                    <Input placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />
        </div>

        <div className='mb-5'>
          <FormField
            name='password'
            control={signInForm.control}
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>
                    {t('signin.form.fields.password')}
                    <FormMessage t={t} />
                  </FormLabel>
                  <FormControl>
                    <Input type='password' placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />
        </div>

        <div>
          <Button type='submit' className='w-full' variant='default'>
            {t('signin.form.submit')}
          </Button>
        </div>
      </form>
    </Form>
  );
}
