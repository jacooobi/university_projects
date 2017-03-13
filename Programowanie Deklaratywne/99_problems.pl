% P01
% find last element of a list

my_last(X, [X]).
my_last(X, [_|T]) :- my_last(X, T), !.

% P02
% find last but one

last_but_one(X, [X, _]).
last_but_one(X, [_|T]) :- last_but_one(X, T), !.

% P03
% find Kth element of a list

find_kth(1, [H|_], H).
find_kth(N, [_|T], R) :- N1 is N-1, find_kth(N1, T, R), !.

% P04
% find the number of elements of a list

my_length([], 0).
my_length([_|T], N) :- my_length(T, N1), N is N1+1, !.

% P05
% reverse a list
% a) using append
% b) using accumulator

my_reverse1([], []).
my_reverse1([H|T], R) :- my_reverse1(T, R1), append(R1, [H], R).

my_reverse2(L, X) :- my_reverse2(L, [], X).
my_reverse2([], A, A).
my_reverse2([H|T], A, R) :- my_reverse2(T, [H|A], R).

% P06
% find out whether a list is a palindrome
% a) using reverse
% b) without reverse

palindrome1(X) :- my_reverse2(X, X).

palindrome2(_) :- !.
palindrome2([_|T]) :- append(T, [_], R), palindrome2(R), !.

% P07
% flatten a nested list structure

my_flatten(X, [X]) :- not(is_list(X)).
my_flatten([], []).
my_flatten([H|T], R) :- my_flatten(H, R1), my_flatten(T, R2), append(R1, R2, R).

% P08
% eliminate consecutive duplicates of list elements

compress([X], [X]).
compress([H,H|T], R) :- compress([H|T], R), !.
compress([H1,H2|T], [H1|R]) :- H1 \= H2, compress([H2|T], R), !.

% P09
% pack consecutive duplicates of list elements into sublists

pack([], []).
pack([H|T], [D|R]) :- transfer(H, T, Tr, D), pack(Tr, R).

transfer(X, [], [], [X]).
transfer(X, [H|T], [H|T], [X]) :- X \= H, !.
transfer(X, [X|T], Tr, [X|Y]) :- transfer(X, T, Tr, Y).

% P10
% Run-length encoding of a list

encode(L, R) :- pack(L, LL), countpacked(LL, R).

countpacked([], []).
countpacked([H|T], [[N, E]|R]) :- length(H, N), my_last(E, H), countpacked(T, R).

% P11
% Modify run-length encoding

encode2(L, R) :- pack(L, LL), countpacked2(LL, R).

countpacked2([], []).
countpacked2([H|T], [E|R]) :- length(H, 1), my_last(E, H), countpacked2(T,R).
countpacked2([H|T], [[N, E]|R]) :- length(H, N), my_last(E, H), countpacked2(T, R).

% P12
% Decode run-length encoding

decode([], []) :- !.
decode([H|T], [D|R]) :- partial(H, D), decode(T, R), !.

partial([N, E], R) :- partial(N, E, R), R \= [N,E].
partial(X, X) :- !.
partial(1, E, [E]) :- !.
partial(N, E, [E|R]) :- N1 is N-1, partial(N1, E, R), !.

% P13
% Run-length encoding of a list (direct solution).


% P14
% Duplicate the elements of a list

dupli([], []).
dupli([H|T], [H,H|R]) :- dupli(T, R).

% P15
% Duplicate the elements of a list a given number of times.
% first solution return a list of lists
% second is a list of integers

duplin1([], _, []).
duplin1([H|T], N, [D|R]) :- duplicate(H, N, D), duplin1(T, N, R).

duplicate(_, 0, []).
duplicate(H, N, [H|R]) :- N1 is N-1, duplicate(H, N1, R).

duplin2(L, N, R) :- duplin2(L, N, R, N).

duplin2([], _, [], _).
duplin2([H|T], N, [H|R], N1) :- N1 > 0, N2 is N1 - 1, duplin2([H|T], N, R, N2), !.
duplin2([_|T], N, R, 0) :- duplin2(T, N, R, N).


% P16
% drop every nth element from a list

drop(L, N, R) :- drop(L, N, R, 1).
drop([], _, [], _).
drop([_|T], N, R, N1) :- mod(N1, N) =:= 0, N2 is N1+1, drop(T, N, R, N2), !.
drop([H|T], N, [H|R], N1) :- N2 is N1+1, drop(T, N, R, N2).

% P17
% split a list into two parts, the length of the first is given (dont use predefined predicates)

split(X, 0, [], X).
split([H|T], N, [H|R], L2) :- N1 is N-1, split(T, N1, R, L2), !.

% P18
% Extract a slice from a list

slice(L, N, M, R) :- slice(L, N, M, R, 1).
slice([H|_], _, M, [H], M).
slice([H|T], N, M, [H|R], N1) :- N =< N1, N2 is N1 + 1, slice(T, N, M, R, N2), !.
slice([_|T], N, M, R, N1) :- N2 is N1+1, slice(T, N, M, R, N2).

% P19
% Rotate a list N places to the left


% P20
% Remove the Kth element from a list

remove_at(X, [X|T], 1, T).
remove_at(X, [H|T], N, [H|T2]) :- N > 1, N1 is N-1, remove_at(X, T, N1, T2).

% P21
% Insert an element at a give position into a list

insert_at(X, [H|T], 1, [X, H|T]).
insert_at(X, [H|T], N, [H|NL]) :- N > 1, N1 is N-1, insert_at(X, T, N1, NL), !.

% P22
% Create a list containing all integers within a given range

range(M, M, [M]).
range(N, M, [N|R]) :- N1 is N+1, range(N1, M, R).

% P23
% Extract a given number of randomly selected elements from a list
% first solution supports duplicates
% second draws unique numbers

rnd_select1(_, 0, []).
rnd_select1(L, N, [X|R]) :- length(L, Len), random_between(1, Len, Num),
    N1 is N-1, remove_at(X, L, Num, _), rnd_select1(L, N1, R), !.

rnd_select(_, 0, []).
rnd_select(L, N, [X|R]) :- N > 0, length(L, Len), M is random(Len) + 1,
    remove_at(X, L, M, T), N1 is N-1, rnd_select(T, N1, R), !.

% P24
% Lotto: Draw N (different heh) random numbers from the set 1..M

rnd_lotto(N, M, L) :- range(1, M, R), rnd_select(R, N, L), !.

% P25
% Generate a random permutation of the elements of a list

rnd_permu(L, R) :- length(L, Len), rnd_select(L, Len, R).

% P31
% Determine whether a given integer number is prime.

is_prime(N) :- is_prime(N, 2).
is_prime(N, M) :- M < N, X is mod(N, M), X \= 0, M1 is M + 1, is_prime(N, M1), !.
is_prime(N, N) :- N =:= N.

% P32
% Determine the greatest common divisor of two positive integer numbers

gcd(X, 0, X) :- X > 0.
gcd(X, Y, G) :- Y > 0, Z is X mod Y, gcd(Y, Z, G).

% P33
% Determine whether two positive integer numbers are coprime

coprime(N, M) :- gcd(N, M, 1), !.

