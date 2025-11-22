import type { OnboardingContentProps } from '../page';
import { useUpdateProfileMutation } from '@/app/api/profiles/query';
import ImageSelector from '@/components/feature/image/ImageSelector';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { MimeType } from '@/constant/mime';
import { zodResolver } from '@hookform/resolvers/zod';
import { useTranslations } from 'next-intl';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import z from 'zod';

const CreateProfileFormSchema = z.object({
  profileImage: z.instanceof(File).optional(),
  name: z.string().min(1),
});
type CreateProfileFormSchemaType = z.infer<typeof CreateProfileFormSchema>;

export default function CreateProfile({ onNextStep }: OnboardingContentProps) {
  const t = useTranslations();

  const createProfileForm = useForm<CreateProfileFormSchemaType>({
    resolver: zodResolver(CreateProfileFormSchema),
    defaultValues: {
      profileImage: undefined,
      name: '',
    },
  });

  const { mutate: updateProfile } = useUpdateProfileMutation();

  const [profileImagePreview, setProfileImagePreview] = useState<
    File | undefined
  >(undefined);

  const createProfileFormSubmitHandler = async (
    values: CreateProfileFormSchemaType,
  ) => {
    updateProfile(
      {
        name: values.name,
      },
      {
        onSuccess: () => {
          onNextStep();
        },
      },
    );
  };

  return (
    <Form {...createProfileForm}>
      <form
        onSubmit={createProfileForm.handleSubmit(
          createProfileFormSubmitHandler,
        )}
      >
        <div className='flex mb-5 w-full'>
          <FormField
            name='profileImage'
            control={createProfileForm.control}
            render={({ field: { onChange } }) => {
              return (
                <FormItem>
                  <FormControl>
                    <ImageSelector
                      onChange={(image) => {
                        setProfileImagePreview(image);
                        onChange(image);
                      }}
                      allowedMimeTypes={[
                        MimeType.IMAGE_JPEG,
                        MimeType.IMAGE_PNG,
                      ]}
                    >
                      <Avatar className='w-16 h-16 mx-5'>
                        <AvatarImage
                          src={
                            profileImagePreview &&
                            URL.createObjectURL(profileImagePreview)
                          }
                        />
                        <AvatarFallback></AvatarFallback>
                      </Avatar>
                    </ImageSelector>
                  </FormControl>
                </FormItem>
              );
            }}
          />

          <FormField
            name='name'
            control={createProfileForm.control}
            render={({ field }) => {
              return (
                <FormItem className='grow'>
                  <FormLabel>
                    {t('onboarding.create-profile.form.fields.name')}
                  </FormLabel>
                  <FormControl>
                    <Input placeholder='' {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />
        </div>

        <Button type='submit' className='w-full' variant='default'>
          {t('onboarding.create-profile.form.continue')}
        </Button>
      </form>
    </Form>
  );
}
