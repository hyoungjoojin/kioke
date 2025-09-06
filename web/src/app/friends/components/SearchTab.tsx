'use client';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import { Routes } from '@/constant/routes';
import { useSearchProfilesQuery } from '@/query/profile/searchProfiles';
import { useDebounce } from '@uidotdev/usehooks';
import Link from 'next/link';
import { useState } from 'react';

export default function SearchTab() {
  const [query, setQuery] = useState('');
  const debouncedQuery = useDebounce(query, 1000);

  const { data: profiles, refetch } = useSearchProfilesQuery(debouncedQuery, {
    enabled: !!debouncedQuery,
  });

  const handleInputSubmit = () => {
    refetch();
  };

  return (
    <>
      <Input
        icon={IconName.SEARCH}
        value={query}
        onChange={(e) => {
          setQuery(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            handleInputSubmit();
          }
        }}
      />

      {profiles?.map((profile, index) => (
        <Card className='my-3' key={index}>
          <CardHeader>
            <CardTitle>
              {profile.email} {profile.name}
            </CardTitle>
          </CardHeader>

          <CardContent className='flex gap-2'>
            <Button>Send Friend Request</Button>
            <Link href={Routes.USER(profile.userId)}>
              <Button>Visit User</Button>
            </Link>
          </CardContent>
        </Card>
      ))}
    </>
  );
}
