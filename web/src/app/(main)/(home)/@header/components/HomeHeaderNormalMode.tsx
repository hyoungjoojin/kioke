import ProfileAvatar from '@/components/feature/profile/ProfileAvatar';
import { Routes } from '@/constant/routes';
import { useTranslations } from 'next-intl';
import Link from 'next/link';

export default function HomeHeaderNormalMode() {
  const t = useTranslations();

  return (
    <>
      <div className='flex gap-5'>
        <span>kioke.</span>
        <span className='hover:underline hover:cursor-pointer'>
          <Link href={Routes.JOURNALS}>{t('header.links.journals')}</Link>
        </span>
        <span className='hover:underline hover:cursor-pointer'>
          <Link href={Routes.FRIENDS}>{t('header.links.friends')}</Link>
        </span>
      </div>

      <div>
        <ProfileAvatar />
      </div>
    </>
  );
}
