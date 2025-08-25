'use client';

import ProfileAvatar from '@/components/feature/profile/ProfileAvatar';
import { Button } from '@/components/ui/button';
import { IconName } from '@/components/ui/icon';
import { Routes } from '@/constant/routes';
import { cn } from '@/lib/utils';
import { useTranslations } from 'next-intl';
import Link from 'next/link';

type BaseHeaderTab = 'home' | 'journals' | 'friends' | 'none';

const BaseHeaderTabs: {
  [k in BaseHeaderTab]: {
    tag: string;
    href: string;
  };
} = {
  ['home']: {
    tag: 'home',
    href: Routes.HOME,
  },
  ['journals']: {
    tag: 'journals',
    href: Routes.JOURNALS,
  },
  ['friends']: {
    tag: 'friends',
    href: Routes.FRIENDS,
  },
  ['none']: {
    tag: '',
    href: '',
  },
};

interface BaseHeaderProps {
  selectedTab: BaseHeaderTab;
}

export default function BaseHeader({ selectedTab }: BaseHeaderProps) {
  const t = useTranslations();

  return (
    <div className='flex justify-between items-center'>
      <div className='flex gap-5'>
        {Object.entries(BaseHeaderTabs)
          .filter(([tab, _]) => tab !== 'none')
          .map(([tab, value], index) => {
            return (
              <span
                className={cn(
                  selectedTab === tab
                    ? 'underline'
                    : 'hover:underline hover:cursor-pointer',
                )}
                key={index}
              >
                <Link href={selectedTab === tab ? '' : value.href}>
                  {t(`header.links.${value.tag}`)}
                </Link>
              </span>
            );
          })}
      </div>

      <div className='flex items-center'>
        <Link href={Routes.NOTIFICATIONS}>
          <Button variant='ghost' icon={IconName.BELL} />
        </Link>
        <ProfileAvatar />
      </div>
    </div>
  );
}
